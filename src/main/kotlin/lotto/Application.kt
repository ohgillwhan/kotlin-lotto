package lotto

import lotto.domain.LottoEachCountCalculator
import lotto.domain.LottoTickets
import lotto.domain.LottoWonNumbers
import lotto.view.input.AmountInput
import lotto.view.InputView
import lotto.view.ResultView
import lotto.view.input.ManualCountInput
import lotto.view.input.ManualLottoInputs
import lotto.view.ouput.LottoRate

fun main() {
    val amountInput: AmountInput = InputView.amoutInput()
    val manualCountInput: ManualCountInput = InputView.inputManualCount(amountInput)
    val manualLottoInput: ManualLottoInputs = InputView.inputManualLottoTickets(manualCountInput)

    val lottoEachCountCalculator = LottoEachCountCalculator(amountInput.lottoCount, manualCountInput.lottoCount)
    val lottoCollection = LottoTickets(lottoEachCountCalculator.autoCount, manualLottoInput.lottoTickets)

    ResultView.printEachTypeCount(lottoEachCountCalculator)
    ResultView.printLotto(lottoCollection)

    val wonNumbers = InputView.inputWonNumber()
    val bonusNumber = InputView.inputBonusNumber()

    val lottoWonNumber = LottoWonNumbers(wonNumbers, bonusNumber.bonusNumber)
    val matchByWonNumbers = lottoWonNumber.match(lottoCollection)

    ResultView.printWon(matchByWonNumbers)
    ResultView.printRate(LottoRate(matchByWonNumbers.sumAmount, amountInput.buyAmount))
}
