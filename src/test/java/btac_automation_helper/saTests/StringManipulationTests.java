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

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import automationHelper.addon.LinkCheckerReporting;
import automationHelper.seleniumappium.DataStructure;
import automationHelper.seleniumappium.StringManipulation;

public class StringManipulationTests
{	
	@Test
	public void appendStringAtIndex() throws Exception
	{
		String[] input={"bcdefghi","abdefghi","abcghi","abcdefg","7toZlSGJWcImnO5glkxqu1p4hs/odCYHUzY9PXGd2qHWYDSgcAbwCxd8gjhLdd7TpbAtlp+QnPUEfqsagwqL7gqEjsenstGENb7Q9N+8werctkageE36AAjE/Y1yo8hPTIp33Mr7ALGjCXdbtub5eORlBfA2TPBvhCldEIQzOS7Vi6e2hIzMPA8G+JuBQ3r8Dqy/qbDOSgILjrPuWvRLdDMK/LLKJ4o3OGYAbkxpkyN6ZVLh0vk0WIUir20iDgcR7xrEHHyxBzxW7iSOjM20/nElvKjSEPAoG8oRDAFYj4WS5kMNssgNzWvIDofA3Bhp3YeacnsHgknJKbPygUW6KeUsgLUnnP3pS1EVafry7Dpx4XAvnAuXmyGQhFbuCSRb6Z2tQrvzhW/+S1oi8XU5AIS7jF2YZXBbr56Zf8CwezRg/cluMrPHfMF40RtwSKt3cWV68EV/ehDnkj859i0609QjDCpFSTBRhX5BptMmKu67v+1raWUPfZ9SD3xoEHbUxDsEhFF5lHUuPmpLDaHJwangcCToPACYsmIVhJOzvglTT2K/aIwoNBoHny90RFGrjMZwWqpOwAm4p+ACIR95J8lDzA6zf0/D5iG+NXR25OMebzgVBcipvHODqpv8+9ZxGKUW4bLRgOvuIMzpzb6IH5nVSxqGuBjk/ZC6SKXci2DVWtKCDTMAdZj+Ner8wHaj6B6tcfSBAQ3GGzZ1zttrmP3hga6dZBmZUUgqbc5kT9MQoY5A9xT1ZDzRf0gK6xJJNh4dvy+nj911FGkPajjZUOtkEVIjobYI3HFfx8yEA3xDUHRyYM8EHBgIu0cTW2QV"};
		int[] index={1,3,4,8,13};
		String[] append={"a","c","def","hi","ysha/"};
		String[] resExpected={"abcdefghi","abcdefghi","abcdefghi","abcdefghi","7toZlSGJWcImysha/nO5glkxqu1p4hs/odCYHUzY9PXGd2qHWYDSgcAbwCxd8gjhLdd7TpbAtlp+QnPUEfqsagwqL7gqEjsenstGENb7Q9N+8werctkageE36AAjE/Y1yo8hPTIp33Mr7ALGjCXdbtub5eORlBfA2TPBvhCldEIQzOS7Vi6e2hIzMPA8G+JuBQ3r8Dqy/qbDOSgILjrPuWvRLdDMK/LLKJ4o3OGYAbkxpkyN6ZVLh0vk0WIUir20iDgcR7xrEHHyxBzxW7iSOjM20/nElvKjSEPAoG8oRDAFYj4WS5kMNssgNzWvIDofA3Bhp3YeacnsHgknJKbPygUW6KeUsgLUnnP3pS1EVafry7Dpx4XAvnAuXmyGQhFbuCSRb6Z2tQrvzhW/+S1oi8XU5AIS7jF2YZXBbr56Zf8CwezRg/cluMrPHfMF40RtwSKt3cWV68EV/ehDnkj859i0609QjDCpFSTBRhX5BptMmKu67v+1raWUPfZ9SD3xoEHbUxDsEhFF5lHUuPmpLDaHJwangcCToPACYsmIVhJOzvglTT2K/aIwoNBoHny90RFGrjMZwWqpOwAm4p+ACIR95J8lDzA6zf0/D5iG+NXR25OMebzgVBcipvHODqpv8+9ZxGKUW4bLRgOvuIMzpzb6IH5nVSxqGuBjk/ZC6SKXci2DVWtKCDTMAdZj+Ner8wHaj6B6tcfSBAQ3GGzZ1zttrmP3hga6dZBmZUUgqbc5kT9MQoY5A9xT1ZDzRf0gK6xJJNh4dvy+nj911FGkPajjZUOtkEVIjobYI3HFfx8yEA3xDUHRyYM8EHBgIu0cTW2QV"};
		for(int i=0;i<input.length;i++)
		{
			String resActual = StringManipulation.appendStringAtIndex(input[i],index[i],append[i]);
			System.out.println("iteration" + i + ": " + resActual);
			Assert.assertTrue(resActual.equals(resExpected[i]),"Failed! appendStringAtIndex Test");					
		}
	}
	
	@Test
	public void deAppendStringAtIndex() throws Exception
	{
		String[] input={"abcdefghi","abcdefghi","abcdefghi","abcdefghi","7toZlSGJWcImysha/nO5glkxqu1p4hs/odCYHUzY9PXGd2qHWYDSgcAbwCxd8gjhLdd7TpbAtlp+QnPUEfqsagwqL7gqEjsenstGENb7Q9N+8werctkageE36AAjE/Y1yo8hPTIp33Mr7ALGjCXdbtub5eORlBfA2TPBvhCldEIQzOS7Vi6e2hIzMPA8G+JuBQ3r8Dqy/qbDOSgILjrPuWvRLdDMK/LLKJ4o3OGYAbkxpkyN6ZVLh0vk0WIUir20iDgcR7xrEHHyxBzxW7iSOjM20/nElvKjSEPAoG8oRDAFYj4WS5kMNssgNzWvIDofA3Bhp3YeacnsHgknJKbPygUW6KeUsgLUnnP3pS1EVafry7Dpx4XAvnAuXmyGQhFbuCSRb6Z2tQrvzhW/+S1oi8XU5AIS7jF2YZXBbr56Zf8CwezRg/cluMrPHfMF40RtwSKt3cWV68EV/ehDnkj859i0609QjDCpFSTBRhX5BptMmKu67v+1raWUPfZ9SD3xoEHbUxDsEhFF5lHUuPmpLDaHJwangcCToPACYsmIVhJOzvglTT2K/aIwoNBoHny90RFGrjMZwWqpOwAm4p+ACIR95J8lDzA6zf0/D5iG+NXR25OMebzgVBcipvHODqpv8+9ZxGKUW4bLRgOvuIMzpzb6IH5nVSxqGuBjk/ZC6SKXci2DVWtKCDTMAdZj+Ner8wHaj6B6tcfSBAQ3GGzZ1zttrmP3hga6dZBmZUUgqbc5kT9MQoY5A9xT1ZDzRf0gK6xJJNh4dvy+nj911FGkPajjZUOtkEVIjobYI3HFfx8yEA3xDUHRyYM8EHBgIu0cTW2QV"};
		int[] index={1,3,4,8,13};
		String[] deAppend={"a","c","def","hi","ysha/"};
		String[] resExpected={"bcdefghi","abdefghi","abcghi","abcdefg","7toZlSGJWcImnO5glkxqu1p4hs/odCYHUzY9PXGd2qHWYDSgcAbwCxd8gjhLdd7TpbAtlp+QnPUEfqsagwqL7gqEjsenstGENb7Q9N+8werctkageE36AAjE/Y1yo8hPTIp33Mr7ALGjCXdbtub5eORlBfA2TPBvhCldEIQzOS7Vi6e2hIzMPA8G+JuBQ3r8Dqy/qbDOSgILjrPuWvRLdDMK/LLKJ4o3OGYAbkxpkyN6ZVLh0vk0WIUir20iDgcR7xrEHHyxBzxW7iSOjM20/nElvKjSEPAoG8oRDAFYj4WS5kMNssgNzWvIDofA3Bhp3YeacnsHgknJKbPygUW6KeUsgLUnnP3pS1EVafry7Dpx4XAvnAuXmyGQhFbuCSRb6Z2tQrvzhW/+S1oi8XU5AIS7jF2YZXBbr56Zf8CwezRg/cluMrPHfMF40RtwSKt3cWV68EV/ehDnkj859i0609QjDCpFSTBRhX5BptMmKu67v+1raWUPfZ9SD3xoEHbUxDsEhFF5lHUuPmpLDaHJwangcCToPACYsmIVhJOzvglTT2K/aIwoNBoHny90RFGrjMZwWqpOwAm4p+ACIR95J8lDzA6zf0/D5iG+NXR25OMebzgVBcipvHODqpv8+9ZxGKUW4bLRgOvuIMzpzb6IH5nVSxqGuBjk/ZC6SKXci2DVWtKCDTMAdZj+Ner8wHaj6B6tcfSBAQ3GGzZ1zttrmP3hga6dZBmZUUgqbc5kT9MQoY5A9xT1ZDzRf0gK6xJJNh4dvy+nj911FGkPajjZUOtkEVIjobYI3HFfx8yEA3xDUHRyYM8EHBgIu0cTW2QV"};
		for(int i=0;i<input.length;i++)
		{
			String resActual = StringManipulation.deAppendStringAtIndex(input[i],index[i],deAppend[i]);
			System.out.println("iteration" + i + ":" + resActual);
			Assert.assertTrue(resActual.equals(resExpected[i]),"Failed! deAppendStringAtIndex Test");					
		}
	}
	
	@Test
	public void replaceCharInString() throws Exception
	{
		Reporter.log(StringManipulation.replaceCharInString("abcdefghi", 1, 'z') ,true);
		Reporter.log(StringManipulation.replaceCharInString("abcdefghi", 9, 'z') ,true);
		Reporter.log(StringManipulation.replaceCharInString("abcdefghi", 3, 'z') ,true);
	}
	
	
	@Test
	public void deleteCharInString() throws Exception
	{
		Reporter.log(StringManipulation.deleteCharInString("abcdef",3).toString() ,true);
		
		Reporter.log(StringManipulation.deleteCharInString("abcdef",1).toString() ,true);
		
		Reporter.log(StringManipulation.deleteCharInString("abcdef",6).toString() ,true);
	}
	
	@Test
	public void regEx() throws Exception
	{
		StringManipulation.regex("404","4\\d\\d");
		System.out.println();
		StringManipulation.regex("40455404","4\\d\\d");


	}
	
	@Test
	public void removeSingleQuoteFromString() throws Exception
	{
		Reporter.log(StringManipulation.removeSingleQuoteFromString("'asssssssssz"),true);
		Reporter.log(StringManipulation.removeSingleQuoteFromString("asssssssssz'"),true);
		Reporter.log(StringManipulation.removeSingleQuoteFromString("'asssssssssz'"),true);
	}
	

}