package lotto.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class LottoWonNumbersTests {
    @Test
    fun `6개 이지만, 중첩되면은 안된다`() {
        assertThrows<IllegalArgumentException> {
            LottoWonNumbers(setOf(1, 2, 3, 4, 5, 1), 10)
        }
    }

    @Test
    fun `6개 이상이면 문제가 없다`() {
        assertDoesNotThrow {
            LottoWonNumbers(setOf(1, 2, 3, 4, 5, 6), 10)
        }
    }

    @Test
    fun `6개 미만이면 문제가 IllegalArgumentException이 발생한다`() {
        assertThrows<IllegalArgumentException> {
            LottoWonNumbers(setOf(1, 2, 3, 4, 5), 10)
        }
    }

    @Test
    fun `기존 로또 번호와 보너스 번호는 겹치면 IllegalArgumentException이 발생한다`() {
        assertThrows<IllegalArgumentException> {
            LottoWonNumbers(setOf(1, 2, 3, 4, 5, 6), 6)
        }
    }

    @Test
    fun `원하는대로 생성해서 당첨 갯수와 총액을 구해보자`() {
        val wonNumber: Set<LottoNumber> = lottoNumberSetOf(1, 2, 3, 4, 5, 6)

        val firstWonNumber = lottoNumberSetOf(1, 2, 3, 4, 5, 6)
        val thirdWonNumber = lottoNumberSetOf(1, 2, 3, 4, 5, 45)
        val fourthWonNumber = lottoNumberSetOf(1, 2, 3, 4, 44, 45)
        val fifthWonNumber = lottoNumberSetOf(1, 2, 3, 43, 44, 45)
        val failWonNUmber = lottoNumberSetOf(1, 2, 42, 43, 44, 45)
        val secondWonNumber = lottoNumberSetOf(1, 2, 3, 4, 5, 10)

        // 1등 2개,
        // 2등 1개
        // 3등 1개
        // 4등 3개
        // 실패 2개
        val lottoTickets: LottoTickets = LottoTickets(
            mutableListOf(
                LottoTicket(firstWonNumber),
                LottoTicket(firstWonNumber),
                LottoTicket(thirdWonNumber),
                LottoTicket(fourthWonNumber),
                LottoTicket(fifthWonNumber),
                LottoTicket(fifthWonNumber),
                LottoTicket(fifthWonNumber),
                LottoTicket(failWonNUmber),
                LottoTicket(failWonNUmber),
                LottoTicket(secondWonNumber),
                LottoTicket(secondWonNumber)
            )
        )

        val matchByWonNumber = LottoWonNumbers(wonNumber, LottoNumber(10)).match(lottoTickets)

        Assertions.assertThat(matchByWonNumber[Rank.FIRST])
            .isEqualTo(2)
        Assertions.assertThat(matchByWonNumber[Rank.THIRD])
            .isEqualTo(1)
        Assertions.assertThat(matchByWonNumber[Rank.FOURTH])
            .isEqualTo(1)
        Assertions.assertThat(matchByWonNumber[Rank.FIFTH])
            .isEqualTo(3)
        Assertions.assertThat(matchByWonNumber[Rank.FAIL])
            .isEqualTo(2)
        Assertions.assertThat(matchByWonNumber[Rank.SECOND])
            .isEqualTo(2)

        Assertions.assertThat(matchByWonNumber.sumAmount)
            .isEqualTo(
                Rank.FIRST.amount * 2 + Rank.THIRD.amount * 1 + Rank.FOURTH.amount * 1 + Rank.FIFTH.amount * 3 + Rank.SECOND.amount * 2
            )
    }

    fun lottoNumberSetOf(vararg numbers: Int) = numbers.map { LottoNumber(it) }.toSet()
    fun LottoNumber(number: Int): LottoNumber = LottoNumber.from(number)
}
