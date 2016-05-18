/**
* Test.java
*
* @version   $Id: Test.java,v_1.4 2014/11/10 2:09:00
*
* @author    hhk9433 (Hrishikesh Karale)
* 
* Revisions:
*      Initial revision
*/


/**
 * The PrintABC class extends thread and over rides the run() which prints the 
 * name of the thread being executed. This class also has a parameterized
 * constructor which accepts object of Test class and integers as present
 * thread and next thread.
 */
class PrintABC extends Thread
{
	//reference of Test class.
	Test test_object;
	//to stores current and next thread values.
	int current_thread, next_Thread;
	
	/**
	 * This parameterised constructor which initializes the class variables.
	 * 
	 * @param test_object
	 * @param present_thread
	 * @param next_Thread
	 */
	PrintABC(Test test_object, int current_thread, int next_Thread)
	{
		//initializes class variable to parameter from constructor.
		this.test_object=test_object;
		//initializes class variable to parameter from constructor.
		this.current_thread=current_thread;
		//initializes class variable to parameter from constructor.
		this.next_Thread =next_Thread;
	}
	
	/**
	 * This is the run method of class PrintABC which has a synchronized 
	 * block of code which checks if the previous thread executed and prints 
	 * name of current thread if previous thread was not the same as current 
	 * thread, whereas it sends the current thread to wait if current thread 
	 * is the same as previous thread  
	 */
	@Override
	public void run()
	{
		try
	        {
				//block synchronized for current test object.
	            synchronized (test_object) 
	            {
	            	//repeated execution of the thread.
				     for (int i = 0; i < 100; i++) 
				     {
				    	/*
				    	 * wait() is called on test object when the status of
				    	 * our thread is not equal to the current thread being
				    	 * executed.
				    	 */
	                	while (test_object.thread_status != current_thread)
	                	{
	                		test_object.wait();
	                	}
	
	                    System.out.print(getName());
	                    test_object.thread_status = next_Thread;
	                    test_object.notifyAll();
				    }
	            }
	        }
	        catch (Exception e) 
	        {
	            System.out.println("Exception 1 :"+e.getMessage());
	        }
	}
}


/*
 *The Test Class is used to execute the code written in class PrintABC.
 */
public class Test 
{
	// keeps track of status of thread.
	int thread_status=1;
	
    /**
     * This is the main method of class test. Objects of class PrintABC are 
     * made and stored in the reference of type class PrintABC.
     * @param args
     */
    public static void main(String[] args)
    {
    	Test test_object = new Test();
	
        PrintABC a=new PrintABC(test_object,1,2);
        //sets name of the object referred  by reference variable a as 'a'.
        a.setName("a");
        PrintABC b=new PrintABC(test_object,2,3);
        //sets name of the object referred  by reference variable b as 'b'.
        b.setName("b");
        PrintABC c=new PrintABC(test_object,3,1);
        //sets name of the object referred  by reference variable c as 'c'.
        c.setName("c");
        
        a.start();
        b.start();
        c.start();
    }
}

