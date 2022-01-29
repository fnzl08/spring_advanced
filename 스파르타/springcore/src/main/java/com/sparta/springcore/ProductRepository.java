package com.sparta.springcore;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//@componenet 쓰면 에러. 밑에 dbid,dburl이런거 어디서 받아오는지 스프링이 모 그래서 빈으로 만든다.
public class ProductRepository {

    public void createProduct(Product product) throws SQLException {
// DB 연결
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");

// DB Query 작성
        PreparedStatement ps = connection.prepareStatement("select max(id) as id from product");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
// product id 설정 = product 테이블의 마지막 id + 1
            product.setId(rs.getLong("id") + 1);
        } else {
            throw new SQLException("product 테이블의 마지막 id 값을 찾아오지 못했습니다.");
        }
        ps = connection.prepareStatement("insert into product(id, title, image, link, lprice, myprice) values(?, ?, ?, ?, ?, ?)");
        ps.setLong(1, product.getId());
        ps.setString(2, product.getTitle());
        ps.setString(3, product.getImage());
        ps.setString(4, product.getLink());
        ps.setInt(5, product.getLprice());
        ps.setInt(6, product.getMyprice());

// DB Query 실행
        ps.executeUpdate();

// DB 연결 해제
        ps.close();
        connection.close();
    }


    ////////////////////////////////////////////////////////////////////////////////
    public Product getProduct(Long id) throws SQLException {
        Product product = new Product();  //넘겨와서 이거 선언해준거

// DB 연결
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");

// DB Query 작성
        PreparedStatement ps = connection.prepareStatement("select * from product where id = ?");
        ps.setLong(1, id);  //클라이언트에서 보낸 아이디가 있는지 체크
  // DB Query 실행
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            product.setId(rs.getLong("id"));
            product.setImage(rs.getString("image"));
            product.setLink(rs.getString("link"));
            product.setLprice(rs.getInt("lprice"));
            product.setMyprice(rs.getInt("myprice"));
            product.setTitle(rs.getString("title"));
        }

// DB 연결 해제
        rs.close();
        ps.close();
        connection.close();

        return product;  //리턴 써주기
    }


    /////////////////////////////////////////////////////////////////////////////////
    public void updateMyprice(Long id, int myprice) throws SQLException {
// DB 연결
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");

// DB Query 작성
        PreparedStatement ps = connection.prepareStatement("update product set myprice = ? where id = ?");
        ps.setInt(1, myprice);    //클라이언트로 올라온 값
        ps.setLong(2, id);   //디비에서 조회한 아이고

// DB Query 실행
        ps.executeUpdate();

// DB 연결 해제
        ps.close();
        connection.close();
    }

    public List<Product> getProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        //////////////////////////////////////////
// DB 연결
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");

// DB Query 작성 및 실행
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from product");

// DB Query 결과를 상품 객체 리스트로 변환
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setImage(rs.getString("image"));
            product.setLink(rs.getString("link"));
            product.setLprice(rs.getInt("lprice"));
            product.setMyprice(rs.getInt("myprice"));
            product.setTitle(rs.getString("title"));
            products.add(product);
        }

// DB 연결 해제
        rs.close();
        connection.close();

        return proucts;  //서비스에 리턴
    }
}

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> { }