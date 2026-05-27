package vending_machine

class VendingMachineImpl(
    private val products: List<Product>
) : VendingMachine {
    private var state: State = State.IDLE
    private var balance: Int = 0 // Plata de la máquina
    private var userBalance: Int = 0 // Plata insertada por el usuario
    private var userProduct: Product? = null

    override fun handleEvent(input: VendingMachineEvent) {
        when(input) {
            VendingMachineEvent.Cancel -> {
                // Devolver dinero al usuario
                if (userBalance > 0) {
                    println("Devolviendo dinero: $userBalance")
                    userBalance = 0
                }

                // Resetear selección de producto
                userProduct = null

                state = State.IDLE
            }
            is VendingMachineEvent.InsertCoin -> {
                // 1 existe producto seleccionado
                if (userProduct != null) {
                    userBalance = input.amount

                    // Validar saldo suficiente
                    if (userBalance >= userProduct!!.price) {
                        // Entregar producto
                        println("Entregando ${userProduct!!.name}")
                        balance += userProduct!!.price
                        userBalance -= userProduct!!.price
                        userProduct = null
                        state = State.IDLE

                        // Devolver cambio si es necesario
                        if (userBalance > 0) {
                            println("Devolviendo cambio: $userBalance")
                            userBalance = 0
                        }
                    } else {
                        println("Saldo insuficiente. El producto cuesta ${userProduct!!.price} y has insertado $userBalance")
                    }
                } else {
                    // En caso que no exista
                    println("Elige un producto antes de insertar monedas")
                    return
                }
            }
            is VendingMachineEvent.SelectProduct -> {
                state = State.WORKING

                // Ask for product
                println("You selected: ${input.product.name} which costs ${input.product.price}")
                val selectedProduct = products.find { it.name == input.product.name }

                // Validado existencia producto
                if (selectedProduct == null) {
                    println("Product not found")
                    return // Loop back to asking for product
                }

                userProduct = selectedProduct
            }
        }
    }

    enum class State {
        IDLE,
        WORKING
    }

    private fun insertCoin(amount: Int) {
        userBalance += amount
    }
}