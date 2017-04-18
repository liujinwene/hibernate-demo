package com.gogo.base.lock.constants;

public enum CoordinationLocks {
	STOCK("stock"),
	CART("cart");
//	ORDER_CANCEL("order_cancel"),
//	ORDER_REFUND("order_refund");
	
	private String code;
    private CoordinationLocks(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public static CoordinationLocks fromCode(String code) {
        for(CoordinationLocks coordinationLock:CoordinationLocks.values()){
            if(coordinationLock.code.equalsIgnoreCase(code)){
                return coordinationLock;
            }
        }
        return null;
    }

}
