/**
 * Nonlinear actuator class
 * 
 * @author Dan Herve
 */

package w15cs350task3;

public class MyActuatorNonlinear extends A_MyActuator implements I_Actuator {
   
   /**
    * The acceleration of the step each update.
    */
   protected double stepAcceleration;
   
   /**
    * @param id  the id of the actuator
    * @param stateStart  the beginning state of the actuator
    * @param stateEnd  the end state of the actuator
    * @param step  the increment to change state at update
    * @param stepAcceleration  the rate that the step increases each update
    * @exception RuntimeException
    *             if the id is an empty string
    * @exception RuntimeException
    *             if the step is not a positive value
    */
   public MyActuatorNonlinear(String id, double stateStart, double stateEnd, double step, double stepAcceleration) {
   
      super(id, stateStart, stateEnd, step);
      
      if(stepAcceleration < 0)
         throw new RuntimeException(this.getClass().getName() + "cannot have a negative value for the stepAcceleration attribute.");
      
      this.stepAcceleration = stepAcceleration;
   }
   
   /**
    * Updates the state from its current state to its
    * next state based on the current step. If the next
    * state exceeds the end state, then the former is
    * clamped to the latter. This returns whether the 
    * end state has been reached.
    *
    * @return boolean
    */ 
   public boolean updateState_() { 
   
      boolean atBound = false;
        
      if(isDead == false) {
      
         if(isDying) {
      
            if(deathTimer == 0) {
               isDying = false;
               isDead = true;
               step += stepAcceleration;
            }
         
            else {
            
               if(positiveStep) {
            
                  state -= step;
               
                  if(state < stateStart) {
               
                     state = stateStart;
                     atBound = true;
                  }
               }
               
               else {
               
                  state += step;
                  
                  if(state > stateStart) {
               
                     state = stateStart;
                     atBound = true;
                  }
               }
               
               if(state == stateStart)
                  atBound = true;
                  
               step /= 2;
               deathTimer--;
            }                        
         }
      
         else {
         
            if(positiveStep) {
         
               if(state < stateEnd) {
            
                  state += step;
               
                  if(state > stateEnd)
                     state = stateEnd;
                  
                  if(state == stateEnd)
                     atBound = true;
               }
            }
            else {
            
               if(state > stateEnd) {
            
                  state -= step;
               
                  if(state < stateEnd)
                     state = stateEnd;                  
                  
                  if(state == stateEnd)
                     atBound = true;
               }
            }
            
            step += stepAcceleration; 
         } 
      }
            
      if(state == stateEnd || atBound || isDead) {
         
         return true;
      }
      
      return false;
   }
   
   /**
    * Overrides the Object class toString method
    *
    * @return String
    */
   @Override
   public String toString() {
   
      return "(id: " + id + ", state: " + state + ", step: " + step + ", dying=" + isDying + ", dead=" + isDead + ")";
   }
}