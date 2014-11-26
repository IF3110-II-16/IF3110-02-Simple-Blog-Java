/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author steve
 */
@Named(value = "logindata")
@SessionScoped
public class logindata {
    String role;
    String nama;
    String namaAsli;
    String email;
    String url = "jdbc:postgresql://localhost:5432/simpleblog";
    String databaseUser = "postgresql";
    String databasePassword = "persib";
    String response;
    Connection connection = null;
    Statement statement = null;
    ResultSet record = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamaAsli() {
        return namaAsli;
    }

    public void setNamaAsli(String namaAsli) {
        this.namaAsli = namaAsli;
    }
    
    /**
     * Creates a new instance of logindata
     */
    public logindata() {     
    }
    
    // Mengembalikan ID dari role user tertentu dalam bentuk integer
    public int ConvertRoleToID() {
        int role_id = 9999;
        
        switch (role) {
            case "Owner":
                role_id = 0;
                break;
            case "Editor":
                role_id = 1;
                break;
            case "Admin":
                role_id = 2;
                break;
            case "Guest":
                role_id = 3;
                break;
        }
        
        return role_id;
    }
    
    public void SafeData() throws SQLException {
        // Konek ke drive jdbc postgresql
        connection = DriverManager.getConnection(url,databaseUser,databasePassword);
        
        // Buat container perintah query ke koneksi drive
        statement = connection.createStatement();
        
        // Insert data nama, email, dan role ke basis data
        int role_id = ConvertRoleToID(); // Konversi role dari string ke integer
        if (role_id != 9999) { // ID role terbentuk
            String insertDataQuery = "INSERT INTO user(username,realname,role,email) VALUES (nama,namaAsli,role_id,email)";
            statement.executeUpdate(insertDataQuery);
        }
        
        // Tutup koneksi dan statement
        connection.close();
        statement.close();
    }
    
    public String login() throws SQLException{ 
        // Penanda apakah username dan password valid atau tidak
        boolean ValidAccount = false;
        // Penanda apakah akun yang dimasukkan akun baru atau bukan
        boolean IsNewAccount = true;
        
        // Konek ke drive jdbc postgresql
        connection = DriverManager.getConnection(url,databaseUser,databasePassword);
        
        // Buat container perintah query ke koneksi drive
        statement = connection.createStatement();
        
        //  Search data username dan role pada database untuk validasi data yang dimasukkan
        record = statement.executeQuery("SELECT * FROM user");
        while (record.next()) {
            String value_username = record.getString("username");
            String value_nama_asli = record.getString("realname");
            int ID_role = record.getInt("role");
            String user_email = record.getString("email");
            
            // data yang dimasukkan dengan di database sesuai
            if ((ID_role == ConvertRoleToID()) && value_username.equals(nama) && user_email.equals(email)) { 
                ValidAccount = true;
            } else {
                if ((user_email.equals(email)) || value_username.equals(nama) || (ID_role == ConvertRoleToID())) { 
                    IsNewAccount = false;
                }
            }
        }
        
        // Tutup koneksi
        connection.close();
        statement.close();
        record.close();
        
        if (ValidAccount) { // Akun valid, asumsi user terkait sudah pernah login sebelumnya
            if (IsNewAccount) { // Akun terkait adalah akun baru yang belum pernah dimasukkan ke database
                SafeData();
            }
            return "successLogin";
        }
        else {
            return "failedLogin";
        }
    }
}
