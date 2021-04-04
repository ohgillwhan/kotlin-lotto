package lotto.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class LottoTicketTests {
    @Test
    fun `로또 생성시 6개의 숫자를 가지고 있어야 한다`() {
        assertDoesNotThrow {
            LottoTicket(순차적으로_증가하는_로또넘버())
        }
    }

    @Test
    fun `로또 번호와 당첨번호와 매칭하여 가져와야 한다`() {
        val lottoTicket: LottoTicket = LottoTicket(순차적으로_증가하는_로또넘버())

        val rank: Rank = LottoWonNumbers(setOf(1, 2, 3, 4, 44, 45), 43).match(
            lottoTicket
        )

        assertThat(rank)
            .isEqualTo(Rank.getRankByCount(4, false))

        assertThat(lottoTicket)
            .containsExactlyInAnyOrder(
                LottoNumber(1),
                LottoNumber(2),
                LottoNumber(3),
                LottoNumber(4),
                LottoNumber(5),
                LottoNumber(6)
            )
    }

    @Test
    fun `5개가 맞고, 1개의 보너스가 맞으면은 2등이다 `() {
        val lottoTicket: LottoTicket = LottoTicket(순차적으로_증가하는_로또넘버())

        val wonNumbers: LottoWonNumbers = LottoWonNumbers(setOf(1, 2, 3, 4, 5, 10), 6)

        assertThat(wonNumbers.match(lottoTicket))
            .isEqualTo(Rank.SECOND)
    }

    @Test
    fun `직접 입력한 로또를 포함하여 자동생성이 가능해야 한다`() {
        val manualTicket = LottoTicket(순차적으로_증가하는_로또넘버())
        val manualLottoTickets = LottoTickets(listOf(manualTicket))

        val lottoTickets = LottoTickets(1, manualLottoTickets)

        assertThat(lottoTickets.size)
            .isEqualTo(2)

        assertThat(lottoTickets)
            .contains(manualTicket)
    }

    fun 순차적으로_증가하는_로또넘버(): Set<LottoNumber> {
        return setOf(
            LottoNumber(1),
            LottoNumber(2),
            LottoNumber(3),
            LottoNumber(4),
            LottoNumber(5),
            LottoNumber(6)
        )
    }

    fun LottoNumber(number: Int): LottoNumber = LottoNumber.from(number)
}
