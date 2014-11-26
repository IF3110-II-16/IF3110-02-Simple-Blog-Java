/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package databaseSession;

import entities.Comments;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author steve
 */
@Stateless
public class CommentsFacade extends AbstractFacade<Comments> {
    @PersistenceContext(unitName = "TubesSimpleBlog2_versi2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentsFacade() {
        super(Comments.class);
    }
    
}
