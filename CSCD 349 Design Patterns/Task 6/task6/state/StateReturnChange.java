package task6.state; 
import task6.Transaction;
import java.util.*;
import task6.money.*;

public class StateReturnChange extends A_State {

   public StateReturnChange(Transaction transaction) {
   
      super(transaction);
      
      List<A_Currency> change = makeChange();
      
      System.out.println("[Machine] returned\n -> ");
      
      for(int i = 0; i < change.size(); i++) {
      
         System.out.println(change.get(i).toString());
      }
   }
   
   @Override
   public StateStartTransaction doStartTransaction() {
   
      return new StateStartTransaction();
   }
   
   public List<A_Currency> makeChange() {
   
      CurrencyManager manager = new CurrencyManager();
   
      return manager.makeChange(getTransaction().getMoneyTendered().subtract(getTransaction().getProductCost()));     
   }
}