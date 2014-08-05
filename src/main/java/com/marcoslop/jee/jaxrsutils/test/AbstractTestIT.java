package com.marcoslop.jee.jaxrsutils.test;

import com.marcoslop.jee.jaxrsutils.JsonSerializator;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import java.io.IOException;

public abstract class AbstractTestIT {

	protected HttpServletRequest request;
    protected HttpSession session;
    
    @Before
	public void preparePersistenceTest() throws Exception {
        startTransaction();
        String sql = getSqlScript();
        if (sql!=null){
            executeScript(sql);
        }
	    prepareObjects ();
	}

    protected abstract EntityManager getEm();
    protected abstract UserTransaction getUtx();
    
    protected void prepareObjects(){
        //TOBE OVERWRITTEN BY CHILDREN
    };
    
    private void startTransaction() throws Exception {
	    getUtx().begin();
	    getEm().joinTransaction();
	}
	
	@After
    public void commitTransaction() throws Exception {
        getUtx().rollback();
    }

    protected void assertJsonCorrect (Object object) throws IOException {
        assertJsonCorrect(object, true);
    }

    protected void assertJsonCorrect (Object object, boolean detach) throws IOException {
        if (detach && object.getClass().isAnnotationPresent(Entity.class) && getEm().contains(object)){
            getEm().detach(object);
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
            Query q =  getEm().createNativeQuery(query);
            q.executeUpdate();
        }
    }

    /**
     * Returns a HttpServletRequest prepared to say that the user has all roles.
     * @return
     */
    protected HttpServletRequest getAllowAllRolesRequest (){
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
        Mockito.when(request.isUserInRole(Mockito.anyString())).thenReturn(true);
        Mockito.when(request.getSession()).thenReturn(session);
        return request;
    }
	
}
