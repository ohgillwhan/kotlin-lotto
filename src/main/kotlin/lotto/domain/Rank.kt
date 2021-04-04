package lotto.domain

enum class Rank(val count: Int, val amount: Long) {
    FAIL(0, 0),
    FIFTH(3, 5_000),
    FOURTH(4, 50_000),
    THIRD(5, 1_500_000),
    SECOND(5, 30_000_000),
    FIRST(6, 2_000_000_000);

    companion object {
        fun getRankByCount(count: Int, matchBonus: Boolean): Rank {
            val secondWonCondition = (count == SECOND.count && matchBonus)
            if (secondWonCondition) {
                return SECOND
            }

            return values().firstOrNull {
                it.count == count
            } ?: FAIL
        }

        fun getWonRank(): List<Rank> {
            return values().filter { it != FAIL }
        }
    }
}
