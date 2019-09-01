/*
 * Date: October 12th 2015
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 * License Type: MIT
 */

package btac_automation_helper.saTests;

import automationHelper.seleniumappium.Database;

public class DatabaseTests
{	
	//test code
		public static void deleteOffice(String deleteOffice) throws Exception
		{
			String[] queryStatements = {
					"SET @name = '"+ deleteOffice +"';",
					"DELETE FROM `m_holiday_office` WHERE `office_id` IN(SELECT `id` FROM `m_office` WHERE `name` = @name);",
					"DELETE FROM `d_oracle_office_details` WHERE `office_id` IN(SELECT `id` FROM `m_office` WHERE `name` = @name);",
					"DELETE FROM `m_office` WHERE `name` = @name;",
			};

			Database.MySqlUpdateQueryExecutor("Deleting an Office...",queryStatements);
		}

}