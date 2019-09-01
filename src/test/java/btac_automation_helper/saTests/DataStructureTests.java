/*
 * Date: October 12th 2015
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 * License Type: MIT
 */

package btac_automation_helper.saTests;

import java.io.IOException;

import org.testng.annotations.Test;

import automationHelper.seleniumappium.DataStructure;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrencyAndDatesTests.
 */
public class DataStructureTests
{	
	@Test
	public void inputGreaterThanArrayItems() throws Exception
	{
		//case1: empty array
		int input1=2016; int[] array1={};
		if(DataStructure.isInputGreaterThanArrayItems(input1, array1))
			System.out.println("input1 '" + input1 + "' is > than all items of array1...");
		else
			System.out.println("input1 '" + input1 + "' is <= any one item of array1...");
		
		//case2: input > all items of array
		int input2=2016; int[] array2={2015,2014,2000};
		if(DataStructure.isInputGreaterThanArrayItems(input2, array2))
			System.out.println("input2 '" + input2 + "' is > than all items of array2...");
		else
			System.out.println("input2 '" + input2 + "' is <= any one item of array2...");

		//case3: input = any one item of array
		int input3=2015; int[] array3={2015,2014,2000};
		if(DataStructure.isInputGreaterThanArrayItems(input3, array3))
			System.out.println("input3 '" + input3 + "' is > than all items of array3...");
		else
			System.out.println("input3 '" + input3 + "' is <= any one item of array3...");

		//case4: input < any one item of array
		int input4=1999; int[] array4={2015,2014,2000};
		if(DataStructure.isInputGreaterThanArrayItems(input4, array4))
			System.out.println("input4 '" + input4 + "' is > than all items of array4...");
		else
			System.out.println("input4 '" + input4 + "' is <= any one item of array4...");
	}
	
	@Test
	public void inputEqualsArrayLastItem() throws Exception
	{
		//case1: empty array
		int input1=2016; int[] array1={};
		if(DataStructure.isInputEqualToArrayLastItem(input1, array1))
			System.out.println("input1 '" + input1 + "' is = last item of array1...");
		else
			System.out.println("input1 '" + input1 + "' is != last item of array1...");
		
		//case2: input > last item of array
		int input2=2016; int[] array2={2015,2014,2000};
		if(DataStructure.isInputEqualToArrayLastItem(input2, array2))
			System.out.println("input2 '" + input2 + "' is = last item of array2...");
		else
			System.out.println("input2 '" + input2 + "' is != last item of array2...");

		//case3: input = last item of array
		int input3=2000; int[] array3={2015,2014,2000};
		if(DataStructure.isInputEqualToArrayLastItem(input3, array3))
			System.out.println("input3 '" + input3 + "' is = last item of array3...");
		else
			System.out.println("input3 '" + input3 + "' is != last item of array3...");

		//case4: input < any one item of array
		int input4=1999; int[] array4={2015,2014,2000};
		if(DataStructure.isInputEqualToArrayLastItem(input4, array4))
			System.out.println("input4 '" + input4 + "' is = last items of array4...");
		else
			System.out.println("input4 '" + input4 + "' is != last item of array4...");
	}
	
	@Test
	public void inputLessThanArrayItem() throws Exception
	{
		//case1: empty array
		int input1=2016; int[] array1={};
		if(DataStructure.isInputLessThanArrayItems(input1, array1))
			System.out.println("input1 '" + input1 + "' is < any one item of array1...");
		else
			System.out.println("input1 '" + input1 + "' is >= any one item of array1 or array is empty...");
		
		//case2: input > all items of array
		int input2=2016; int[] array2={2015,2014,2000};
		if(DataStructure.isInputLessThanArrayItems(input2, array2))
			System.out.println("input2 '" + input2 + "' is < any one item of array2...");
		else
			System.out.println("input2 '" + input2 + "' is >= items of array2...");

		//case3: input = any one item of array
		int input3=2014; int[] array3={2015,2014,2000};
		if(DataStructure.isInputLessThanArrayItems(input3, array3))
			System.out.println("input3 '" + input3 + "' is < any one item of array3...");
		else
			System.out.println("input3 '" + input3 + "' is >= items of array3...");

		//case4: input < any one item of array
		int input4=1999; int[] array4={2015,2014,2000};
		if(DataStructure.isInputLessThanArrayItems(input4, array4))
			System.out.println("input4 '" + input4 + "' is < any one item of array4...");
		else
			System.out.println("input4 '" + input4 + "' is >= items of array4...");

		//case5: input < any one item of array
		int input5=2013; int[] array5={2015,2010,2000};
		if(DataStructure.isInputLessThanArrayItems(input5, array5))
			System.out.println("input5 '" + input5 + "' is < any one item of array5...");
		else
			System.out.println("input5 '" + input5 + "' is >= items of array5...");

	}
}