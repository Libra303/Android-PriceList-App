# Android-PriceList-App

1.Prototype1 -> 리스트 검색,추가,수정,삭제 기능구현 완료

<앞으로 해야 할 일>

1.라이브러리로 엑셀파일 읽어서 ROOM DB 저장// O
2.현재 싱글스레드를 멀티 스레드로 변경
3.코드 개선
4.UI개선


<버그 사항>
맨위 상품 연속 삭제시 및에 상품이 삭제된다
=> 삭제 이벤트 완료후 notifyDataSetChanged(); 해야하는데
notifyItemRemoved();로 작성하여 인덱스가 밀리는 현상이 발생했음

