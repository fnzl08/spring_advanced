package com.sparta.springcore;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class AllInOneController {

    // 신규 상품 등록
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto) throws SQLException {
//    ProductService productService = new ProductService();  -- 이거 컨트롤러를 서비스 분리할때 써줄. 객체 생성
//    Product product = productService.createProduct(requestDto); -- 관심상품 만들어줘 하는 함수. 인풋과 아웃풋 정해주면 인텔리제이에서 함수 자동으로 만들어준다.
        //create에 클라이언트에게 받은 requestdto가 인풋, 프로덕트 정보가 아웃풋. createproduct에서 오른쪽 버튼 눌러서 만들래?하는거 ㅇㅇ하면 서비스에 함수 만들어짐
        //그 밑에 db연결 해지까지 넘겨주자


        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto);

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

// 응답 보내기
        return product;
    }

    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {
        //ProductService productService = new ProductService();
        //Product product = productService.updaterProduct(id, requestDto(); 클라이언트에게 받은 id 를 dto로 넘겨주고, 프로덕트 정보에 반환을 받아서 뒤에 return에 나옴
        //update 어쩌고 클래스 만들어주기 누르고 밑에 넘겨

        Product product = new Product();

// DB 연결
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:springcoredb", "sa", "");

// DB Query 작성
        PreparedStatement ps = connection.prepareStatement("select * from product where id = ?");
        ps.setLong(1, id);

// DB Query 실행
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            product.setId(rs.getLong("id"));
            product.setImage(rs.getString("image"));
            product.setLink(rs.getString("link"));
            product.setLprice(rs.getInt("lprice"));
            product.setMyprice(rs.getInt("myprice"));
            product.setTitle(rs.getString("title"));
        } else {
            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
        }

// DB Query 작성
        ps = connection.prepareStatement("update product set myprice = ? where id = ?");
        ps.setInt(1, requestDto.getMyprice());
        ps.setLong(2, product.getId());

// DB Query 실행
        ps.executeUpdate();

// DB 연결 해제
        rs.close();
        ps.close();
        connection.close();

// 응답 보내기 (업데이트된 상품 id)
        return product.getId();
    }

    // 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> getProducts() throws SQLException {
//        ProductService productService = new ProductService();
//        List<Product> products = productService.getProducts(); 쪼갤 때 적고 겟에 클래스 만들기

        List<Product> products = new ArrayList<>();

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

// 응답 보내기
        return products;
    }
}