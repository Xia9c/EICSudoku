package com.hust.eicsudoku

import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.gridlayout.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.collections.HashMap
import com.developer.kalert.KAlertDialog


class PlayScreen : Fragment(), View.OnClickListener {
    private var selectedButton: Button? = null
    private var userInputBoard = Array(9) { IntArray(9) { 0 } }
    private var matrix = Array(9) { IntArray(9) { 0 } }
    private var buttonMap = HashMap<String, Button>()
    private var timerVal: CountDownTimer? = null
    private val initialSolveTime = 3600000L
    private lateinit var timer: TextView
    private lateinit var resetButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_play_screen, container, false)
        resetButton = view.findViewById(R.id.resetButton)
        resetButton.setOnClickListener(this)

        timer = view.findViewById(R.id.timer)
        timerVal = object : CountDownTimer(initialSolveTime, 1000) {
            override fun onTick(p0: Long) {
                val alreadyPassed = initialSolveTime - p0
                val minutes = (alreadyPassed / 1000 / 60).toInt()
                val seconds = (alreadyPassed / 1000).toInt() % 60
                var displayTimerText: String = if (minutes / 10 == 0) "0$minutes:" else "$minutes:"
                displayTimerText += if (seconds / 10 == 0) "0$seconds" else "$seconds"
                timer.text = displayTimerText
            }

            override fun onFinish() {
                KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
                    .setTitleText("You've spent an hour")
                    .setContentText("Here is the solution!")
                    .show()
                if (selectedButton != null) {
                    selectedButton!!.setBackgroundResource(R.drawable.button_border)
                    removeHighlight(selectedButton!!)
                }
                setBoard()
            }
        }
        (timerVal as CountDownTimer).start()

        requireActivity().onBackPressed()
        requireActivity().onBackPressed()

        val sudokuMatrix: RelativeLayout = view.findViewById(R.id.sudokuMatrix)
        for (i in 0 until sudokuMatrix.childCount) {
            val matrixButton = sudokuMatrix.getChildAt(i)
            val buttonString = matrixButton.toString()
            val startInd = buttonString.lastIndexOf("app:id")
            val subStr = buttonString.substring(startInd + 7, startInd + 10)
            buttonMap[subStr] = matrixButton as Button
        }

        generateMatrix()
        for ((_, v) in buttonMap) {
            if (v.isClickable)
                v.setOnClickListener(this)
        }

        val inputButtons: GridLayout = view.findViewById(R.id.inputButtons)
        for (i in 0 until inputButtons.childCount) {
            inputButtons.getChildAt(i).setOnClickListener(this)
        }
        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        timerVal?.cancel()
    }

    override fun onClick(v: View?) {
        if (v?.isClickable == false) {
            return
        }
        if (resetButton.text.toString() == "BACK" && v?.id != resetButton.id) {
            return
        }
        when (v?.id) {
            R.id.clear -> {
                selectedButton?.text = ""
                if (selectedButton != null) {
                    val (row, col) = getButtonPosition(selectedButton!!)
                    if (col != null && row != null) {
                        userInputBoard[row - 1][col - 1] = 0
                    }
                }
                return
            }
            R.id.resetButton -> {
                if (resetButton.text.toString() == "BACK") {
                    timerVal?.cancel()
                    requireActivity().onBackPressed()
                    return
                }
                resetBoard()
                return
            }
            R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine -> {
                val numButton: Button = requireView().findViewById(v.id)
                selectedButton?.text = numButton.text.toString()
                if (selectedButton != null) {
                    val (row, col) = getButtonPosition(selectedButton!!)
                    if (col != null && row != null) {
                        userInputBoard[row - 1][col - 1] = numButton.text.toString().toInt()
                    }
                }
                check()
                return
            }
            else -> {
                if (selectedButton != null) {
                    selectedButton!!.setBackgroundResource(R.drawable.button_border)
                    removeHighlight(selectedButton!!)
                }
                selectedButton = v as Button
                highlight(selectedButton!!)
                selectedButton!!.setBackgroundResource(R.drawable.selected_button_border)
                return
            }
        }
    }

    //由Button控件对象获得Button坐标
    private fun getButtonPosition(button: Button): Pair<Int?, Int?> {
        val buttonString = button.toString()
        val startInd = buttonString.lastIndexOf("app:id")
        val subStr = buttonString.substring(startInd + 7, startInd + 10)
        if (subStr[0] != 'b') return Pair(null, null)
        return Pair(subStr[1].digitToIntOrNull(), subStr[2].digitToIntOrNull())
    }

    //Highlight指选中的格子那一行和列里的其他可选格子
    private fun removeHighlight(button: Button) {
        if (selectedButton == null) return

        val (row, col) = getButtonPosition(button)
        for (i in 0..8) {
            if (row != null) {
                if (buttonMap["b${row}${i + 1}"]?.isClickable!!) {
                    buttonMap["b${row}${i + 1}"]?.setBackgroundResource(R.drawable.button_border)
                }
            }
            if (col != null) {
                if (buttonMap["b${i + 1}${col}"]?.isClickable!!) {
                    buttonMap["b${i + 1}${col}"]?.setBackgroundResource(R.drawable.button_border)
                }
            }
        }
    }

    private fun highlight(button: Button) {
        val (row, col) = getButtonPosition(button)

        for (i in 0..8) {
            if (row != null) {
                if (buttonMap["b${row}${i + 1}"]?.isClickable!!) {
                    buttonMap["b${row}${i + 1}"]?.setBackgroundResource(R.drawable.highlighted_button)
                }
            }
            if (col != null) {
                if (buttonMap["b${i + 1}${col}"]?.isClickable!!) {
                    buttonMap["b${i + 1}${col}"]?.setBackgroundResource(R.drawable.highlighted_button)
                }
            }
        }
    }

    private fun resetBoard() {
        if (selectedButton == null) return
        removeHighlight(selectedButton!!)
        selectedButton!!.setBackgroundResource(R.drawable.button_border)
        selectedButton = null
        for (i in 0..8) {
            for (j in 0..8) {
                if (buttonMap["b${i + 1}${j + 1}"]?.isClickable!!) {
                    buttonMap["b${i + 1}${j + 1}"]?.text = ""
                    userInputBoard[i][j] = 0
                }
            }
        }
    }

    private fun check() {
        val isRow = Array(9) { BooleanArray(9) { false } }
        val isCol = Array(9) { BooleanArray(9) { false } }
        val isSubMatrix = Array(3) { Array(3) { BooleanArray(9) { false } } }
        for (i in 0..8)
            for (j in 0..8) {
                if (userInputBoard[i][j] == 0) return
                isRow[i][userInputBoard[i][j] - 1] = true
                isCol[j][userInputBoard[i][j] - 1] = true
                isSubMatrix[i / 3][j / 3][userInputBoard[i][j] - 1] = true
            }
        for (i in 0..8)
            for (k in 0..8)
                if (!isRow[i][k] or !isCol[i][k]) return
        for (i in 0..2)
            for (j in 0..2)
                for (k in 0..8)
                    if (!isSubMatrix[i][j][k]) return
        KAlertDialog(activity, KAlertDialog.SUCCESS_TYPE)
            .setContentText("GREAT JOB, YOU SOLVED IT IN " + timer.text + " !")
            .show()
        timerVal!!.cancel()
        resetButton.text = getString(R.string.back)
    }

    //生成答案矩阵
    private fun generateMatrix() {
        //设定一个初始种子后，搜索得到一个数独解矩阵，再在(1..9)间随机映射
        matrix[0][0] = (1..9).random()
        solveSudoku(9)
        val randomMap = mutableListOf<Int>()
        for (num in 1..9) randomMap.add(num)
        randomMap.shuffle()
        for (i in 0..8)
            for (j in 0..8)
                matrix[i][j] = randomMap[matrix[i][j] - 1]
        //根据所选难度设置题目中已知数字数量
        var solvedCnt = when (arguments?.getString("difficulty_text").toString()) {
            "easy" -> 78
            "medium" -> 42
            else -> 35
        }
        while (solvedCnt > 0) {
            val i = (0..8).random()
            val j = (0..8).random()
            val key = "b${(i + 1)}${(j + 1)}"
            if (buttonMap[key]?.isClickable == false) {
                continue
            }
            //初始化已知数字的矩阵
            userInputBoard[i][j] = matrix[i][j]
            buttonMap[key]?.text = matrix[i][j].toString()
            buttonMap[key]?.setTextColor(Color.parseColor("#ffffff"))
            buttonMap[key]?.setBackgroundResource(R.drawable.selected_button_border)
            buttonMap[key]?.setTypeface(null, BOLD)
            buttonMap[key]?.isClickable = false
            solvedCnt--
        }
    }

    //(row,col)处填入num是否合法
    private fun isSafe(row: Int, col: Int, num: Int): Boolean {
        for (d in 0..8) {
            if (d == col) continue
            if (matrix[row][d] == num) return false
        }
        for (r in 0..8) {
            if (r == row) continue
            if (matrix[r][col] == num) return false
        }

        val sqrt = 3
        val boxRowStart = row - row % sqrt
        val boxColStart = col - col % sqrt

        for (r in boxRowStart until (boxRowStart + sqrt)) {
            for (d in boxColStart until (boxColStart + sqrt)) {
                if (d == col && r == row) continue
                if (matrix[r][d] == num) return false
            }
        }
        return true
    }

    //搜索得到一个数独解矩阵
    private fun solveSudoku(N: Int): Boolean {
        var row = -1
        var col = -1
        var isEmpty = true
        for (i in 0 until N) {
            for (j in 0 until N) {
                if (matrix[i][j] == 0) {
                    row = i
                    col = j
                    isEmpty = false
                    break
                }
            }
            if (!isEmpty) break
        }
        if (isEmpty) return true
        for (num in 1 until N + 1) {
            if (isSafe(row, col, num)) {
                matrix[row][col] = num
                if (solveSudoku(N)) return true
                else matrix[row][col] = 0
            }
        }
        return false
    }

    //显示结果
    private fun setBoard() {
        for (i in 0..8)
            for (j in 0..8) {
                buttonMap["b${i + 1}${j + 1}"]?.text = "${matrix[i][j]}"
            }
        resetButton.text = getString(R.string.back)
    }
}