package com.hust.eicsudoku

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.collections.HashMap
import com.developer.kalert.KAlertDialog


class PlayScreen : Fragment(), View.OnClickListener {
    private var selectedButton: Button? = null
    private var userInputBoard = Array(9) { IntArray(9) { 0 } }
    private var matrix = Array(9) { IntArray(9) { 0 } }
    private var inputButtonMap = HashMap<String, Button>()
    private var buttonMap = HashMap<String, Button>()
    private var reverseMap = HashMap<Button, String>()
    private var timerVal: CountDownTimer? = null
    private val initialSolveTime = 3600000L
    private var solvedcnt: Int = 56
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

        buttonMap["b11"] = view.findViewById(R.id.b11)
        buttonMap["b12"] = view.findViewById(R.id.b12)
        buttonMap["b13"] = view.findViewById(R.id.b13)
        buttonMap["b14"] = view.findViewById(R.id.b14)
        buttonMap["b15"] = view.findViewById(R.id.b15)
        buttonMap["b16"] = view.findViewById(R.id.b16)
        buttonMap["b17"] = view.findViewById(R.id.b17)
        buttonMap["b18"] = view.findViewById(R.id.b18)
        buttonMap["b19"] = view.findViewById(R.id.b19)
        buttonMap["b21"] = view.findViewById(R.id.b21)
        buttonMap["b22"] = view.findViewById(R.id.b22)
        buttonMap["b23"] = view.findViewById(R.id.b23)
        buttonMap["b24"] = view.findViewById(R.id.b24)
        buttonMap["b25"] = view.findViewById(R.id.b25)
        buttonMap["b26"] = view.findViewById(R.id.b26)
        buttonMap["b27"] = view.findViewById(R.id.b27)
        buttonMap["b28"] = view.findViewById(R.id.b28)
        buttonMap["b29"] = view.findViewById(R.id.b29)
        buttonMap["b31"] = view.findViewById(R.id.b31)
        buttonMap["b32"] = view.findViewById(R.id.b32)
        buttonMap["b33"] = view.findViewById(R.id.b33)
        buttonMap["b34"] = view.findViewById(R.id.b34)
        buttonMap["b35"] = view.findViewById(R.id.b35)
        buttonMap["b36"] = view.findViewById(R.id.b36)
        buttonMap["b37"] = view.findViewById(R.id.b37)
        buttonMap["b38"] = view.findViewById(R.id.b38)
        buttonMap["b39"] = view.findViewById(R.id.b39)
        buttonMap["b41"] = view.findViewById(R.id.b41)
        buttonMap["b42"] = view.findViewById(R.id.b42)
        buttonMap["b43"] = view.findViewById(R.id.b43)
        buttonMap["b44"] = view.findViewById(R.id.b44)
        buttonMap["b45"] = view.findViewById(R.id.b45)
        buttonMap["b46"] = view.findViewById(R.id.b46)
        buttonMap["b47"] = view.findViewById(R.id.b47)
        buttonMap["b48"] = view.findViewById(R.id.b48)
        buttonMap["b49"] = view.findViewById(R.id.b49)
        buttonMap["b51"] = view.findViewById(R.id.b51)
        buttonMap["b52"] = view.findViewById(R.id.b52)
        buttonMap["b53"] = view.findViewById(R.id.b53)
        buttonMap["b54"] = view.findViewById(R.id.b54)
        buttonMap["b55"] = view.findViewById(R.id.b55)
        buttonMap["b56"] = view.findViewById(R.id.b56)
        buttonMap["b57"] = view.findViewById(R.id.b57)
        buttonMap["b58"] = view.findViewById(R.id.b58)
        buttonMap["b59"] = view.findViewById(R.id.b59)
        buttonMap["b61"] = view.findViewById(R.id.b61)
        buttonMap["b62"] = view.findViewById(R.id.b62)
        buttonMap["b63"] = view.findViewById(R.id.b63)
        buttonMap["b64"] = view.findViewById(R.id.b64)
        buttonMap["b65"] = view.findViewById(R.id.b65)
        buttonMap["b66"] = view.findViewById(R.id.b66)
        buttonMap["b67"] = view.findViewById(R.id.b67)
        buttonMap["b68"] = view.findViewById(R.id.b68)
        buttonMap["b69"] = view.findViewById(R.id.b69)
        buttonMap["b71"] = view.findViewById(R.id.b71)
        buttonMap["b72"] = view.findViewById(R.id.b72)
        buttonMap["b73"] = view.findViewById(R.id.b73)
        buttonMap["b74"] = view.findViewById(R.id.b74)
        buttonMap["b75"] = view.findViewById(R.id.b75)
        buttonMap["b76"] = view.findViewById(R.id.b76)
        buttonMap["b77"] = view.findViewById(R.id.b77)
        buttonMap["b78"] = view.findViewById(R.id.b78)
        buttonMap["b79"] = view.findViewById(R.id.b79)
        buttonMap["b81"] = view.findViewById(R.id.b81)
        buttonMap["b82"] = view.findViewById(R.id.b82)
        buttonMap["b83"] = view.findViewById(R.id.b83)
        buttonMap["b84"] = view.findViewById(R.id.b84)
        buttonMap["b85"] = view.findViewById(R.id.b85)
        buttonMap["b86"] = view.findViewById(R.id.b86)
        buttonMap["b87"] = view.findViewById(R.id.b87)
        buttonMap["b88"] = view.findViewById(R.id.b88)
        buttonMap["b89"] = view.findViewById(R.id.b89)
        buttonMap["b91"] = view.findViewById(R.id.b91)
        buttonMap["b92"] = view.findViewById(R.id.b92)
        buttonMap["b93"] = view.findViewById(R.id.b93)
        buttonMap["b94"] = view.findViewById(R.id.b94)
        buttonMap["b95"] = view.findViewById(R.id.b95)
        buttonMap["b96"] = view.findViewById(R.id.b96)
        buttonMap["b97"] = view.findViewById(R.id.b97)
        buttonMap["b98"] = view.findViewById(R.id.b98)
        buttonMap["b99"] = view.findViewById(R.id.b99)

        generatematrix()
        for ((k, v) in buttonMap) {
            reverseMap[v] = k
            if (v.isClickable)
                v.setOnClickListener(this)
        }

        inputButtonMap["1"] = view.findViewById(R.id.one)
        inputButtonMap["2"] = view.findViewById(R.id.two)
        inputButtonMap["3"] = view.findViewById(R.id.three)
        inputButtonMap["4"] = view.findViewById(R.id.four)
        inputButtonMap["5"] = view.findViewById(R.id.five)
        inputButtonMap["6"] = view.findViewById(R.id.six)
        inputButtonMap["7"] = view.findViewById(R.id.seven)
        inputButtonMap["8"] = view.findViewById(R.id.eight)
        inputButtonMap["9"] = view.findViewById(R.id.nine)
        inputButtonMap["c"] = view.findViewById(R.id.clear)
        for ((_, v) in inputButtonMap) {
            v.setOnClickListener(this)
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
                    val row = reverseMap[selectedButton]?.get(1)?.toString()?.toInt()
                    val col = reverseMap[selectedButton]?.get(2)?.toString()?.toInt()
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
                    val row = reverseMap[selectedButton]?.get(1)?.toString()?.toInt()
                    val col = reverseMap[selectedButton]?.get(2)?.toString()?.toInt()
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

    //Highlight指选中的格子那一行和列里的其他可选格子
    private fun removeHighlight(button: Button) {
        if (selectedButton == null) return

        val row = reverseMap[button]?.get(1)?.toString()?.toInt()
        val col = reverseMap[button]?.get(2)?.toString()?.toInt()

        for (i in 0 until 9) {
            if (row != null) {
                if (buttonMap["b${row}${i + 1}"]?.isClickable!!) {
                    buttonMap["b${row}${i + 1}"]?.setBackgroundResource(R.drawable.button_border)
                }
            }
        }
        for (i in 0 until 9) {
            if (col != null) {
                if (buttonMap["b${i + 1}${col}"]?.isClickable!!) {
                    buttonMap["b${i + 1}${col}"]?.setBackgroundResource(R.drawable.button_border)
                }
            }
        }
    }

    private fun highlight(button: Button) {
        val row = reverseMap[button]?.get(1)?.toString()?.toInt()
        val col = reverseMap[button]?.get(2)?.toString()?.toInt()

        for (i in 0 until 9) {
            if (row != null) {
                if (buttonMap["b${row}${i + 1}"]?.isClickable!!) {
                    buttonMap["b${row}${i + 1}"]?.setBackgroundResource(R.drawable.highlighted_button)
                }
            }
        }
        for (i in 0 until 9) {
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
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (buttonMap["b${i + 1}${j + 1}"]?.isClickable!!) {
                    buttonMap["b${i + 1}${j + 1}"]?.text = ""
                    userInputBoard[i][j] = 0
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun check() {
        for (i in 0..8)
            for (j in 0..8) {
                if (userInputBoard[i][j] == 0) {
                    return
                }
                for (k in 0..8) {
                    if (userInputBoard[i][j] == userInputBoard[i][k] && j != k) {
                        return
                    }
                    if (userInputBoard[i][j] == userInputBoard[k][j] && i != k) {
                        return
                    }
                }
            }
        for (a in 0..2)
            for (b in 0..2) {
                for (i in 0..2)
                    for (j in 0..2) {
                        val r = 3 * a
                        val c = 3 * b
                        for (x in 0..2)
                            for (y in 0..2) {
                                if (userInputBoard[i + r][j + c] == userInputBoard[x + r][y + c] && (x != i || y != j)) {
                                    return
                                }
                            }
                    }
            }
        KAlertDialog(activity, KAlertDialog.SUCCESS_TYPE)
            .setContentText("GREAT JOB, YOU SOLVED IT IN " + timer.text + " !")
            .show()
        timerVal!!.cancel()
        resetButton.text = "BACK"
    }

    //生成答案矩阵
    private fun generatematrix() {
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
        val difficultyLevel = arguments?.getString("difficulty_text").toString()
        solvedcnt = when (difficultyLevel) {
            "easy" -> 80
            "medium" -> 42
            else -> 35
        }
        while (solvedcnt > 0) {
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
            solvedcnt--
        }
    }

    //(row,col)处填入num是否合法
    private fun isSafe(row: Int, col: Int, num: Int): Boolean {
        for (d in 0 until 9) {
            if (d == col)
                continue
            if (matrix[row][d] == num) {
                return false
            }
        }
        for (r in 0 until 9) {
            if (r == row)
                continue
            if (matrix[r][col] == num) {
                return false
            }
        }

        val sqrt = 3
        val boxRowStart = row - row % sqrt
        val boxColStart = col - col % sqrt

        for (r in boxRowStart until (boxRowStart + sqrt)) {
            for (d in boxColStart until (boxColStart + sqrt)) {
                if (d == col && r == row)
                    continue
                if (matrix[r][d] == num)
                    return false
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