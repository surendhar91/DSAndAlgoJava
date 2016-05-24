/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ds.geeks.binarytree;

import java.util.Stack;

/**
 *
 * @author surendhar-2481
 */
public class InnerClassTest {
    

    static String[] Braces(String[] values) {
        String[] result = new String[values.length];
        for(int i=0;i<values.length;i++){
            if(areParanthesisBalanced(values[i])){
                result[i] = "YES";
                
            }else{
                result[i]="NO";
            }
            
        }
        return result;

    }
    static boolean isMatchingPair(char character1,char character2){
		if(character2=='{'&&character1=='}'){
				return true;
		}else if(character2=='('&&character1==')'){
				return true;
		}else if(character2=='['&&character1==']'){
				return true;
		}else{
				return false;
		}
    }

    static boolean areParanthesisBalanced(String exp){
         int i;
	Stack<Character> stack= new Stack<Character>();
        if(!(exp.length()>0))return false;
	for(i=0;i<exp.length();i++){
		  if(exp.charAt(i)=='{'||exp.charAt(i)=='['||exp.charAt(i)=='('){
                      stack.push(exp.charAt(i));
                    }else if(exp.charAt(i)=='}'||exp.charAt(i)==']'||exp.charAt(i)==')'){
			
			if(stack.isEmpty()){
                            return false;
                        }else if(!isMatchingPair(exp.charAt(i),stack.pop())){
				return false;
			}
			
		}else{
              return false;
          }
	}
	if(stack.isEmpty()){
			return true;
	}else{
			return false;
	}
        
    }


    
    public static void main(String[] args) {
        String[] str = {"{}[]()","{[}]","({})","]{}[","[{)]","{}[]()",
"{[}]","]()"};
        String[] res = Braces(str);
        for(int i=0;i<res.length;i++){
            System.out.println(res[i]);
        }
    }
}
