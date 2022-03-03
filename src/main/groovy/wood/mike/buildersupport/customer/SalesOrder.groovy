package wood.mike.buildersupport.customer

/**
 * Mimicking a Grails domain
 */
class SalesOrder {
    String sku
    Integer amount
    Double price

    void save() {
        println 'saving sales order'
    }
}
