import java.util.*;

public class yoda3 
{  
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        System.out.println("\nInfix expression: " + input);
        System.out.println("\nInfix to postfix expression: "+ infixToPostfix(input));
        System.out.println("\nPostfix evaluation: "+ calculate(input));
    }

    static int precedence(char op) 
    {
        
        switch (op) 
        {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0; // '('
        }
    }

    static List<Object> infixToPostfix(String s) 
    {
        Stack<Character> stack = new Stack<Character>();
        List<Object> postfix = new LinkedList<Object>();

        int numbBuff = 0;
        boolean bufferingOperand = false;
        
        for (char c : s.toCharArray()) //every character in string
        {
            if (c >= '0' && c <= '9') //if digit
            {
                numbBuff = numbBuff * 10 + c - '0';
                bufferingOperand = true;
            } 
            
            else 
            {
                if (bufferingOperand)
                {
                    postfix.add(numbBuff);
                }

                numbBuff = 0;
                bufferingOperand = false;

                if (c == ' ' || c == '\t') //if single or tab space
                {
                    continue;
                }

                if (c == '(') 
                {
                    stack.push('(');
                } 
                
                else if (c == ')') 
                {
                    while (stack.peek() != '(')
                    {
                        postfix.add(stack.pop());
                    }
                    stack.pop(); // popping "("
                } 
                
                else 
                { // operator
                    while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek()))
                    {
                        postfix.add(stack.pop());
                    }
                    stack.push(c);
                }
            }

        }
        if (bufferingOperand)
        {
            postfix.add(numbBuff);
        }

        while (!stack.isEmpty())
        {
            postfix.add(stack.pop());
        }

        return postfix;
    }

    static int evaluatePostfix(List<Object> postfix) 
    {
        Stack<Integer> stack = new Stack<Integer>();
        double a = 0.0, b = 0.0;
        for (Object s : postfix) 
        {
            if (s instanceof Character) 
            {
                char c = (Character) s;
                b = stack.pop();
                a = stack.pop();
                switch (c) 
                {
                    case '+':
                        stack.push((int) (a + b));
                        break;
                    case '-':
                        stack.push((int) (a - b));
                        break;
                    case '*':
                        stack.push((int) (a * b));
                        break;
                    default:
                        stack.push((int) (a / b));
                }
            } 
            
            else 
            { // instanceof Integer
                stack.push((Integer) s);
            }
        }
        return stack.pop();
    }

    public static double calculate(String s) 
    {
        return evaluatePostfix(infixToPostfix(s));
    }

}