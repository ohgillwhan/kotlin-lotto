package lotto.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LottoTicketsTests {
    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5])
    fun `여러개의 로또를 구매가 가능하다`(count: Int) {
        val lottoTickets: LottoTickets = LottoTickets(count)

        assertThat(lottoTickets.size)
            .isEqualTo(count)
    }
}
