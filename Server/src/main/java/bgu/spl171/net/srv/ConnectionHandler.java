/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl171.net.srv;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * @author bennyl
 */
public interface ConnectionHandler<T> extends Closeable{

    void send(T msg) ; // Added this function, as per https://www.cs.bgu.ac.il/~spl171/index.php?page=Assignments.Assignment_3

}
