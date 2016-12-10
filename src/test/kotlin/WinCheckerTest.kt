import eightbitlab.game.WinChecker
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class WinCheckerTest {
    private class TestCase(val test: Array<CharArray>, val result: Boolean, val lastCharacter: Char)
    private val testSubject = WinChecker()

    private val tests = object : ArrayList<TestCase>() {
        init {
            add(TestCase(Resource.boardNoWinRLast, false, 'R'))
            add(TestCase(Resource.boardWinRUp, true, 'R'))
            add(TestCase(Resource.boardWinRDiagonal, true, 'R'))
            add(TestCase(Resource.boardWinRHorizontal, true, 'R'))
            add(TestCase(Resource.boardWinYUp, true, 'Y'))
            add(TestCase(Resource.boardWinYUp2, true, 'Y'))
            add(TestCase(Resource.boardWinRHorizontalTop, true, 'R'))
        }
    }

    @Test
    fun win_condition_is_correct() {
        tests.forEach {
            val result = testSubject.checkWin(it.lastCharacter, it.test)
            assertEquals(it.result, result)
        }
    }
}
