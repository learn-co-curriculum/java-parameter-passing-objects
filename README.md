# Passing and Returning Objects

## Learning Goals

- Review implicit and explicit method parameters
- Pass an object to a method
- Return an object from a method

## Code Along

Fork and clone this lesson to run the code.

## Introduction

In this lesson we will learn how to pass an object as an argument to a method,
and how to return an object from a method.  

## Method Parameters

Recall that parameters are variables defined within parentheses in the method declaration
after the method name. Arguments are the actual values that are passed to the parameters
when the method is invoked. The declared data type for a method parameter
can be a primitive type (`int`, `double`, `boolean`, etc) or a reference type such as
an array, `String`, or any other class.

Consider a simple `BankAccount` class with instance methods `deposit` and `withdraw`
that update the account `balance` based on the parameter named `amount`.

```java
public class BankAccount {
    private String id;
    private int balance;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    @Override
    public String toString() {
        return id + " balance = " + balance ;
    }

    public static void main(String[] args) {
        BankAccount checkingAccount = new BankAccount();
        checkingAccount.id = "checking account";
        BankAccount savingsAccount = new BankAccount();
        savingsAccount.id = "savings account";

        checkingAccount.deposit(100);
        System.out.println(checkingAccount);  //checking account balance = 100

        savingsAccount.deposit(2000);
        System.out.println(savingsAccount);  //savings account balance = 2000

        savingsAccount.withdraw(75);
        System.out.println(savingsAccount);  //savings account balance = 1925

    }
}
```

The program prints:

```text
checking account balance = 100
savings account balance = 2000
savings account balance = 1925
```

Since `deposit` and `withdraw` are instance methods, they must be invoked with a `BankAccount`
object. As we learned in the previous lesson, the implicit parameter `this` will
reference the invocation object.  The explicit parameter `amount` is assigned the
value passed as an argument in the method call.


| Code                            | Java Visualizer                                                                                           |
|---------------------------------|-----------------------------------------------------------------------------------------------------------|
| `checkingAccount.deposit(100);` | ![checking deposit](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/checking_this.png) |

Try stepping through with the debugger and observe the value assigned to the
implicit parameter `this` and explicit parameter `amount` for each method call.


```java
checkingAccount.deposit(100);
System.out.println(checkingAccount);  //checking account balance = 100

savingsAccount.deposit(2000);
System.out.println(savingsAccount);  //savings account balance = 2000

savingsAccount.withdraw(75);
System.out.println(savingsAccount);  //savings account balance = 1925
```

We can see the final object state after executing the two deposits and one withdrawal:

![final bankaccount state](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/bankaccount_state.png)

## Passing an object to a method

We will add a new method named `transfer` that lets us transfer funds between two accounts by
withdrawing from one account and depositing to the other.

For example, the following method call should withdraw $500 from the savings account and
deposit 500 into the checking account:

`savingsAccount.transfer(500, checkingAccount);`

1. The implicit parameter is a reference to the withdrawal account.
2. The first parameter is the amount to transfer.
3. The second parameter is a reference to the deposit account.

The method signature should be as shown below.  The parameters do not include a withdrawal
account since that can be referenced by the implicit parameter `this`:

`public void transfer(int amount, BankAccount depositAccount)`

Before we implement the method, consider the algorithm to perform the transfer. We
will not handle the case of insufficient funds.

1. Call the `withdraw` method using a reference to the withdrawal account (`this`).
2. Call the `deposit` method using a reference to the deposit account (`depositAccount`).

The `transfer` method implementation is therefore:  

```java
public void transfer(int amount, BankAccount depositAccount) {
    this.withdraw(amount);
    depositAccount.deposit(amount);
}
```

Note we could omit `this.` and simply call `withdraw(amount)` as shown below.
However, it can be helpful to specify explicit object references when
working with multiple instances of the same class.

```java
public void transfer(int amount, BankAccount depositAccount) {
    withdraw(amount);
    depositAccount.deposit(amount);
}
```

We'll update the `main` to call the `transfer` method and then print the resulting object state.

```java
public class BankAccount {
    private String id;
    private int balance;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void transfer(int amount, BankAccount depositAccount) {
        this.withdraw(amount);
        depositAccount.deposit(amount);
    }

    @Override
    public String toString() {
        return id + " balance = " + balance ;
    }

    public static void main(String[] args) {
        BankAccount checkingAccount = new BankAccount();
        checkingAccount.id = "checking account";
        BankAccount savingsAccount = new BankAccount();
        savingsAccount.id = "savings account";

        checkingAccount.deposit(100);
        System.out.println(checkingAccount);  //checking account balance = 100

        savingsAccount.deposit(2000);
        System.out.println(savingsAccount);  //savings account balance = 2000

        savingsAccount.withdraw(75);
        System.out.println(savingsAccount);  //savings account balance = 1925

        savingsAccount.transfer(500, checkingAccount);
        System.out.println(checkingAccount);  //checking account balance = 600
        System.out.println(savingsAccount);  //savings account balance = 1425

    }
}
```

Let's debug this program to see what happens!  Set a breakpoint
on the line that calls the `transfer` method:


![transfer breakpoint](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/transfer_breakpoint.png)

The debugger stops at the breakpoint.  The object state before calling `savingsAccount.transfer(500, checkingAccount);`
is as shown:

![before transfer](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/before_transfer.png)

Press step-into to see the call stack frame for the method call `savingsAccount.transfer(500, checkingAccount);`.

Notice `this` references the savings account and `depositAccount` references the checking account:

![call transfer](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/call_transfer.png)


Press step-into to execute the method call `this.withdraw(amount)`.  We can
see a new frame added to the  stack for the `withdraw` method call, with `this` referencing
the savings account:

![call withdraw](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/call_withdraw.png)

Press step-over to execute the decrement operation `balance -= amount;`.

![decrement balance](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/decrement_balance.png)

Press step-over again to finish the `withdraw` method and return control to the `transfer` method.  Notice
the `withdraw` method is no longer on the call stack, and the debugger is ready to execute the next line
of code `depositAccount.deposit(amount);`:

![return to transfer method](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/withdraw_return.png)

Press step-into to execute the call `depositAccount.deposit(amount);`.  The method is added to the call stack,
with `this` referencing the checking account:

![deposit call](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/deposit_call.png)

Press step-over to execute the increment operation `balance += amount;`.

![increment balance](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/increment_balance.png)

Press step-over again to finish the `deposit` method and return control to the `transfer` method.
The `deposit` method is no longer on the call stack, and the debugger is ready to 
return out of the `transfer` method:

![complete transfer method](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/complete_transfer.png)

Press step-over again to complete the `transfer` method and return control to the `main`.
You can press the green "resume" button to execute the remaining print statements
and confirm the output as shown below.

```text
checking account balance = 100
savings account balance = 2000
savings account balance = 1925
checking account balance = 600
savings account balance = 1425
```


## Returning an object from a method

We've just seen how to pass an object into a method through a parameter.
Now let's see how to pass an object out of a method using a return statement.

The `Course` class has instance variables to store the course name, number of credits,
and section number.

```java
public class Course {

    private String name;
    private int credits;
    private int section;

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", credits=" + credits +
                ", section=" + section ;
    }

    public static void main(String[] args) {
        Course sqlSection1 = new Course();
        sqlSection1.name = "Intro to SQL";
        sqlSection1.credits = 3;
        sqlSection1.section = 1;

        System.out.println(sqlSection1);
    }

}
```

The `main` creates a `Course` instance and prints the object state:

```text
name='Intro to SQL', credits=3, section=1
```

Let's update the class to add a method called `cloneCourse`.
The method will create a new `Course` instance that has
the same name and number of credits as the current course, but
a different section number.  The method returns
the new `Course` object.  Eventually we will see how to
define a constructor method to do this, but for now
we'll implement this as an instance method. 

```java
 public Course cloneCourse(int section) {
     Course clone = new Course();
     clone.name = this.name;
     clone.credits = this.credits;
     clone.section = section;
     return clone;
}
```

Note that we could omit `this.` in the assignment statements,
but for clarity we include it since the method deals with two
`Course` instances.

The `main` method can call `cloneCourse` passing the new section number
as an argument:

```java
// create a new course section by cloning an existing course
Course sqlSection2 = sqlSection1.cloneCourse(2);
```

The `Course` class definition is thus:

```java
public class Course {

    private String name;
    private int credits;
    private int section;

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", credits=" + credits +
                ", section=" + section ;
    }

    public Course cloneCourse(int section) {
        Course clone = new Course();
        clone.name = this.name;
        clone.credits = this.credits;
        clone.section = section;
        return clone;
    }

    public static void main(String[] args) {
        Course sqlSection1 = new Course();
        sqlSection1.name = "Intro to SQL";
        sqlSection1.credits = 3;
        sqlSection1.section = 1;

        // create a new course section by cloning an existing course
        Course sqlSection2 = sqlSection1.cloneCourse(2);

        System.out.println(sqlSection1);
        System.out.println(sqlSection2);

    }

}
```

Let's set a breakpoint in the `main` method at the call to the new method:

`Course sqlSection2 = sqlSection1.cloneCourse(2);`

Press step-into to transfer control into the `cloneCourse` method.  
Notice `this` references the existing course created in the `main` method.

![course step into](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/course_step0.png)

Press step-over to execute each statement in the `cloneCourse` method:


| Code                            | Java Visualizer                                                                                       |
|---------------------------------|-------------------------------------------------------------------------------------------------------|
| `Course clone = new Course();`  | ![course step1](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/course_step1.png)  |
| `clone.name = this.name;`       | ![course step2](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/course_step2.png)  |
| `clone.credits = this.credits;` | ![course step3](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/course_step3.png)  |
| `clone.section = section;`      | ![course step4](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/course_step4.png)  |
| `return clone;`                 |                                                                                                       |

The  `main` method assigns the returned object to the variable `sqlSection2`:

![course step5](https://curriculum-content.s3.amazonaws.com/6676/java-mod2-strings/course_step5.png)

## Conclusion

The declared data type for a method parameter can be a primitive or reference type.
Similarly, a method may return a primitive or reference as a value.

