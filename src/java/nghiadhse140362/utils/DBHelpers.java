/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiadhse140362.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author haseo
 */
public final class DBHelpers {
    public static Connection makeConnection() throws NamingException, SQLException{
        Context curContext = new InitialContext();
        Context tomcatContext = (Context)curContext.lookup("java:comp/env");
        DataSource ds = (DataSource)tomcatContext.lookup("YellowMoonDB");
        return ds.getConnection();
    }
}
