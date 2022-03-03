package wood.mike.buildersupport.customer

/**
 * Mimicking a Grails domain
 */
class Invoice {
    List<SalesOrder> orders = []

    void addToOrders( SalesOrder salesOrder ) {
        orders << salesOrder
    }

    void save() {
        println 'saving invoice'
    }
}
