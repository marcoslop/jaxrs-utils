package com.marcoslope.jee.jaxrsutils.test;

import com.marcoslope.jee.jaxrsutils.JsonSerializator;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
import java.io.IOException;

public abstract class AbstractTestIT {

	@PersistenceContext
    protected EntityManager em;
    
    @Inject
    UserTransaction utx;

    protected HttpServletRequest request;
    
    @Before
	public void preparePersistenceTest() throws Exception {
        startTransaction();
        String sql = getSqlScript();
        if (sql!=null){
            executeScript(sql);
        }
	    prepareObjects ();
	}
    
    protected void prepareObjects(){
        //TOBE OVERWRITTEN BY CHILDREN
    };
    
    private void startTransaction() throws Exception {
	    utx.begin();
	    em.joinTransaction();
	}
	
	@After
    public void commitTransaction() throws Exception {
        utx.rollback();
    }

    protected void assertJsonCorrect (Object object) throws IOException {
        assertJsonCorrect(object, true);
    }

    protected void assertJsonCorrect (Object object, boolean detach) throws IOException {
        if (detach){
            em.detach(object);
        }
        new JsonSerializator().writeToString(object);
    }

    protected String getSqlScript (){
        //TOBE OVERWRITTEN BY CHILDREN
        return null;
    }

    protected void executeScript (String sql) throws Exception {
        String[] queries = sql.split(";");
        for (String query : queries) {
            Query q =  em.createNativeQuery(query);
            q.executeUpdate();
        }
    }

    /**
     * Returns a HttpServletRequest prepared to say that the user has all roles.
     * @return
     */
    protected HttpServletRequest getAllowAllRolesRequest (){
        request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.isUserInRole(Mockito.anyString())).thenReturn(true);
        return request;
    }
	
}
