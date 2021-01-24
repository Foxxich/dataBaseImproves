package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//Example of protected variation
public interface DataAccessObject {

    boolean checkUser(String user, String password);

    ArrayList<DataBaseObject> selectKlient();

    ArrayList<DataBaseObject> selectZamowienie();

    ArrayList<DataBaseObject> selectElementZamowienia();

    ArrayList<DataBaseObject> selectTowar();

    /*
     *  This method is used for inserting of products.
     */
    void addTowar(Towar towar) throws SQLException;

    /*
     *  This method is used for inserting of clients.
     */
    void addKlient(Klient klient) throws SQLException;

    /*
     *  This method is used for inserting of invoices.
     */
    void addZamowienie(Zamowienie zamowienie) throws SQLException;

    /*
     *  This method is used for inserting of elements of invoices.
     */
    void addElement(ElementZamowienia elementZamowienia) throws SQLException;

    /*
     *  This method is used for updating of products.
     */
    void updateTowar(Towar towar) throws SQLException;

    /*
     *  This method is used for updating of clients.
     */
    void updateKlient(Klient klient) throws SQLException;

    /*
     *  This method is used for updating of invoices.
     */
    void updateZamowienie(Zamowienie zamowienie) throws SQLException;

    /*
     *  This method is used for updating of elements of invoices.
     */
    void updateElement(ElementZamowienia elementZamowienia) throws SQLException;

    /*
     *  This method is used for getting all invoices with given id of client
     */
    void returnZamowienia(Klient klient);

    /*
     *  This method is used for getting all invoice elements with given id of invoice
     */
    void returnElementyZamowienia(Zamowienie zamowienie);

    /*
     *  This method is used for getting all invoice elements with given discount
     */
    void returnElementyZeZnizka(ElementZamowienia elementZamowienia);

    /*
     *  This method is used for getting all invoices with given id of item
     */
    void returnZamowieniaZTowarem(Towar towar);

    void deleteKlient(int klientID) throws SQLException;

    void deleteZamowienie(int zamowienieID) throws SQLException;

    void deleteElementZamowienia(int elementZamowieniaID) throws SQLException;

    void deleteTowar(int towarID) throws SQLException;
}