package lotto.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class LottoNumberGeneratorTests {

    @Test
    fun `로또 넘버 제너레이터로 로또가 정상적으로 만들어지는지 확인`() {
        val wonNumber: Set<LottoNumber> = setOf(
            LottoNumber(1),
            LottoNumber(2),
            LottoNumber(3),
            LottoNumber(4),
            LottoNumber(5),
            LottoNumber(6)
        )

        val firstWonNumber = 원하는_대로_만들어_주는_제너레이터(random = listOf(1, 2, 3, 4, 5, 6))

        val lottoTickets = LottoTickets(1, firstWonNumber)

        val matchByWonNumber = LottoWonNumbers(wonNumber, LottoNumber(10)).match(lottoTickets)

        Assertions.assertThat(matchByWonNumber[Rank.FIRST])
            .isEqualTo(1)
    }

    fun 원하는_대로_만들어_주는_제너레이터(random: List<Int>): LottoNumberGenerator {

        return object : LottoNumberGenerator {
            override fun pickNumber(): Set<LottoNumber> = random.map(::LottoNumber).toSet()
        }
    }
}
