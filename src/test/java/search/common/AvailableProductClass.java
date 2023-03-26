package search.common;

public enum AvailableProductClass {
	ORANGE,
	APPLE,
	PASTA,
	COLA;
	
	 @Override
     public String toString() {
         return this.name().toLowerCase();
     }
	 
	 public static boolean contains(String searchedProduct) {

		    for (AvailableProductClass e : AvailableProductClass.values()) {
		        if (e.name().equals(searchedProduct)) {
		            return true;
		        }
		    }
		    return false;
		}
}
