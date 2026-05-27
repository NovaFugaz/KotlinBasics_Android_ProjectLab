package vending_machine

fun main() {
    val inventory = mutableMapOf(
        Product("Coke", 150) to 5,
        Product("Pepsi", 130) to 5,
        Product("Water", 100) to 5
    )

    val vendingMachine: VendingMachine = VendingMachineV3Impl(inventory)

//    while(true) {
//        // Handle CLI text to VendingMachineEvent conversion here (not implemented in this example)
//        break
//    }

//     시뮬레이션: 제품 선택, 동전 투입, 제품 선택 변경, 취소 등
    vendingMachine.handleEvent(VendingMachineEvent.SelectProduct(Product("Coke", 150)))
    vendingMachine.handleEvent(VendingMachineEvent.InsertCoin(100))
    vendingMachine.handleEvent(VendingMachineEvent.InsertCoin(50))
    vendingMachine.handleEvent(VendingMachineEvent.SelectProduct(Product("Pepsi", 130)))
    vendingMachine.handleEvent(VendingMachineEvent.Cancel)
}