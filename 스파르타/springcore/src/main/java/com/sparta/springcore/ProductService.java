package com.sparta.springcore;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component //객체 생셍
public class ProductService {

    public Product createProduct(ProductRequestDto requestDto) throws SQLException {  //뒤에는 exception 이리로 올려준거
        Product product = new Product(requestDto);

        ProductRepository productRepository = new ProductRepository();   //Repository떼낼때
//        productRepository.createProduct(product); 이거를 스프링데이터jpa쓰면
          productRepository.save(product);
        //디비에 인풋은 프로덕트 정보. 아웃풋은 컨트롤러에게 프로덕트 넘겨주니까 굳이 리턴값 받을 필욘 없다. 오른쪽 버튼 눌러서 함수 만들기, 디비 역할 넘겨주자
        return product; //리턴 설정 완료
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) throws SQLException {
        ProductRepository productRepository = new ProductRepository();   //레파지토리로 보낼 거 자르위해 선언
//        Product product = productRepository.getProduct(id);  //디비에 아이디 알아야 프로덕트 정보 알 수 있으니까 프로덕트로 반환, 겟프로덕트 클래스 만들어주는거 해주고
        Product product = productRepository.findbyId(id)
                .orElseThrow(() -> new NullPointerException("해당 아이디 없음")); //findbyid 들어가보면 optional이라서 Optional <Product> product 해주던가 이거
//        if (product == null) {   //없으면 에러. 이거 서비스가 한다.
//            throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
//        }

        int myprice = requestDto.getMyprice();
        product.setMyprice(myprice); //클라이언트에서 온 dto의 마이프라이스 가지고 세트
        productRepository.save(product);
//        productRepository.updateMyprice(id, myprice);   //repo에게 업데이트 맡길거야. myprice update할거니까 dto에서 클라이언트 요청이니까ㅣ 겟 마이프라이스
        //반환은 없지, 밑에 디비 옮겨주기 repo디

        return product;  //리턴값도 설정 완료
    }
/////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Product> getProducts() //throws SQLException {  :: @Repository특징인데 exception지가 다 함
//        ProductRepository productRepository = new ProductRepository();   //repo로 넘어가게
//        List<Product> products = productRepository.getProducts(); //get으로 . 나머지 repo로 넘기턴
        List<Product> products = productRepository.findAll();
        return products;  //리턴값 설정. 넘겨주게
    }
}