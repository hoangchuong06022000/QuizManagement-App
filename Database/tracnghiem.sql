-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 10, 2021 lúc 05:56 PM
-- Phiên bản máy phục vụ: 10.4.22-MariaDB
-- Phiên bản PHP: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `tracnghiem`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cauhoi`
--

CREATE TABLE `cauhoi` (
  `STT` int(3) NOT NULL,
  `maDeThi` varchar(6) COLLATE utf8_bin NOT NULL,
  `tenCauHoi` varchar(1000) COLLATE utf8_bin NOT NULL,
  `cauA` varchar(200) COLLATE utf8_bin NOT NULL,
  `cauB` varchar(200) COLLATE utf8_bin NOT NULL,
  `cauC` varchar(200) COLLATE utf8_bin NOT NULL,
  `cauD` varchar(200) COLLATE utf8_bin NOT NULL,
  `dapAn` varchar(200) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `cauhoi`
--

INSERT INTO `cauhoi` (`STT`, `maDeThi`, `tenCauHoi`, `cauA`, `cauB`, `cauC`, `cauD`, `dapAn`) VALUES
(1, 'DE0001', 'Câu 1. Rễ cây trên cạn hấp thụ nước và ion muối khoáng chủ yếu qua', 'miền lông hút', 'miền chóp rễ.\r\n', 'miền sinh trưởng.', 'miền trưởng thành.', 'miền lông hút.'),
(1, 'DE0002', 'Câu 1: Nguyên nhân chủ yếu tạo nên sự tương phản về trình độ phát triển kinh tế - xã hội giữa nhóm nước phát triển với đang phát triển là', 'Thành phần chủng tộc và tôn giáo.', 'Quy mô dân số và cơ cấu dân số.', 'Trình độ khoa học – kĩ thuật.', 'Điều kiện tự nhiên và tài nguyên thiên nhiên.', 'Trình độ khoa học – kĩ thuật.'),
(1, 'DE0003', 'Câu 1: Có mấy khổ giấy chính?', '2', '3', '4', '5', '5'),
(1, 'DE0004', 'Câu 1: Thiết kế nhằm mục đích gì?', 'Xác định hình dạng', 'Xác định kích thước', 'Xác định kết cấu và chức năng', 'Cả 3 đáp án trên', 'Cả 3 đáp án trên'),
(1, 'DE0005', 'Câu 1. Ý nào sau đây không phù hợp với loài vượn cổ trong quá trình tiến hóa thành người ? ', 'Sống cách đây 6 triệu năm.', 'Có thể đứng và đi bằng 2 chân.', 'Tay được dung để cầm nắm.', 'Chia thành các chủng tộc lớn.', 'Chia thành các chủng tộc lớn.'),
(2, 'DE0001', 'Câu 2. Lông hút rất dễ gẫy và sẽ tiêu biến ở môi trường', 'quá ưu trương, quá axit hay thiếu oxi.\r\n', 'quá nhược trương, quá axit hay thiếu oxi.', 'quá nhược trương, quá kiềm hay thiếu oxi.', 'quá ưu trương, quá kiềm hay thiếu oxi.', 'quá ưu trương, quá axit hay thiếu oxi.'),
(2, 'DE0002', 'Câu 2: Ý nào sau đây không phải là biểu hiện về trình độ phát triển kinh tế - xã hội của các nước phát triển?', 'Đầu tư ra nước ngoài nhiều', 'Dân số đông và tăng nhanh', 'GDP bình quân đầu người cao', 'Chỉ số phát triển ccon người ở mức cao', 'Dân số đông và tăng nhanh'),
(2, 'DE0003', 'Câu 2: Tên các khổ giấy chính là:', 'A. A0, A1, A2', 'A0, A1, A2, A3', 'A3, A1, A2, A4', 'A0, A1, A2, A3, A4', 'A0, A1, A2, A3, A4'),
(2, 'DE0004', 'Câu 2: Thiết kế gồm mấy giai đoạn?', '4', '5', '6', '7', '5'),
(2, 'DE0005', 'Câu 2. Xương hóa thạch của loài vượn cổ được tìm thấy ở đâu?', 'Đông Phi, Tây Á, Bắc Á.', 'Đông Phi, Tây Á, Đông Nan Á.', 'Đông Phi, Việt Nam, Trung Quốc.', 'Tây Á, Trung Á, Bắc Mĩ.', 'Đông Phi, Việt Nam, Trung Quốc.'),
(3, 'DE0001', 'Câu 3. Sự xâm nhập của nước vào tế bào lông hút theo cơ chế\r\n', 'thẩm thấu', 'cần tiêu tốn năng lượng', 'nhờ các bơm ion', 'chủ động', 'thẩm thấu'),
(3, 'DE0002', 'Câu 3: Biểu hiện về trình độ phát triển kinh tế - xã hội của các nước đang phát triển không bao gồm', 'Nợ nước ngoài nhiều ', 'GDP bình quân đầu người thấp', 'Tỉ lệ gia tăng dân số tự nhiên cao ', 'Chỉ số phát triển con người ở mức thấp', 'Tỉ lệ gia tăng dân số tự nhiên cao '),
(3, 'DE0003', 'Câu 3: Trong các khổ giấy chính, khổ giấy có kích thước lớn nhất là:', 'A0', 'A1', 'A4', 'Các khổ giấy có kích thước như nhau', 'A0'),
(3, 'DE0004', 'Câu 3: Làm mô hình thử nghiệm, chế tạo thử” thuộc giai đoạn?', '3', '4', '5', '6', '3'),
(3, 'DE0005', 'Câu 3. Di cốt của người tối cổ được tìm thấy ở đâu?', 'Đông Phi, Trung Quốc, Bắc Âu.', 'Đông Phi, Tây Á, Bắc Âu.', 'Đông Phi, Indonexia, Đông Nam Á.', 'Tây Á, Trung Quốc, Bắc Âu.', 'Đông Phi, Indonexia, Đông Nam Á.'),
(4, 'DE0001', 'Câu 4. Sự hấp thụ ion khoáng thụ động của tế bào rễ cây phụ thuộc vào', 'hoạt động trao đổi chất. ', 'chênh lệch nồng độ ion.', 'cung cấp năng lượng. ', 'hoạt động thẩm thấu.', 'chênh lệch nồng độ ion.'),
(4, 'DE0002', 'Câu 4: Nước nào dưới đây thuộc các nước công nghiệp mới (NICs)?', 'Hoa Kì, Nhật Bản, Pháp', 'Pháp, Bô-li-vi-a, Việt Nam', 'Ni-giê-ri-a, Xu-đăng, Công-gô', 'Hàn Quốc, Bra-xin, Ác-hen-ti-na', 'Hàn Quốc, Bra-xin, Ác-hen-ti-na'),
(4, 'DE0003', 'Câu 4: Trên mỗi bản vẽ có:', 'Khung bản vẽ', 'Khung tên', 'Khung bản vẽ và khung tên', 'Khung bản vẽ hoặc khung tên', 'Khung bản vẽ và khung tên'),
(4, 'DE0004', 'Câu 4: Sau khi thẩm định, đánh giá phương án thiết kế:', 'Tiến hành làm mô hình thử nghiệm, chế tạo thử', 'Tiến hành lập hồ sơ kĩ thuật', 'Nếu không đạt thì tiến hành lập hồ sơ kĩ thuật', 'Nếu đạt thì tiến hành lập hồ sơ kĩ thuật', 'Nếu đạt thì tiến hành lập hồ sơ kĩ thuật'),
(4, 'DE0005', 'Câu 4: Người tối cổ có bước tiến hóa hơn về cấu tạo cơ thể so với loài vượn cổ ở điểm nào?', 'Đã đi, đứng bằng hai chân, đôi bàn tay được giải phóng.', 'Trán thấp và bợt ra sau, u mày nổi cao.', 'Hộp sọ lớn hơn, đã hình thành trung tâm phát tiếng nói trong não.', 'Đã loại bỏ hết dấu tích vượn trên cơ thể.', 'Hộp sọ lớn hơn, đã hình thành trung tâm phát tiếng nói trong não.'),
(5, 'DE0001', 'Câu 5. Trong các đặc điểm sau:\r\n(1) Thành phần tế bào mỏng, không có lớp cutin bề mặt.\r\n(2) Thành tế bào dày.\r\n(3) Chỉ có một không bào trung tâm lớn.\r\n(4) Áp suất thẩm thấu lớn.\r\nTế bào lông hút ở rễ cây có bao nhiêu đặc điểm?', '1', '2', '3', '4', '3'),
(5, 'DE0002', 'Câu 5: Điểm khác biệt trong cơ cấu GDP phân theo khu vực kinh tế của nhóm nước phát triển so với nhóm nước đang phát triển là:', 'Tỉ trọng khu vực III rất cao', 'Tỉ trọng khu vực II rất thấp', 'Tỉ trọng khu vực I còn cao', 'Cân đối về tỉ trọng giữa các khu vực', 'Tỉ trọng khu vực III rất cao'),
(5, 'DE0003', 'Câu 5: Phát biểu nào sau đây sai?', 'Lề trái bản vẽ có kích thước 20 mm', 'Lề phải bản vẽ có kích thước 10 mm', 'Lề trên bản vẽ có kích thước 10 mm', 'Lề trái bản vẽ có kích thước 10 mm', 'Lề trái bản vẽ có kích thước 10 mm'),
(5, 'DE0004', 'Câu 5: Hồ sơ kĩ thuật gồm: ', 'Các bản vẽ tổng thể và chi tiết của sản phẩm', 'Các bản thuyết minh tính toán về sản phẩm', 'Các chỉ dẫn về vận hành, sử dụng sản phẩm', 'Cả 3 đáp án trên', 'Cả 3 đáp án trên'),
(5, 'DE0005', 'Câu 5. Trong quá trình tiến hóa từ vượn thành người. Người tối cổ được đánh giá', 'Vẫn chưa thoát thai khỏi loài vượn.', 'Là bước chuyển tiếp từ vượn thành người.', 'Là những chủ nhân đầu tiên trong lịch sử loài người.', 'Là những con người thông minh.', 'Là những chủ nhân đầu tiên trong lịch sử loài người.'),
(6, 'DE0001', 'Câu 6. Phần lớn các ion khoáng xâm nhập vào rễ theo cơ chế chủ động, diễn ra theo phương thức vận chuyển từ nơi có', 'nồng độ cao đến nơi có nồng độ thấp, cần tiêu tốn ít năng lượng.', 'nồng độ cao đến nơi có nồng độ thấp.', 'nồng độ thấp đến nơi có nồng độ cao, không đòi hỏi tiêu tốn năng lượng.', 'nồng độ thấp đến nơn có nồng độ cao, đòi hỏi phải tiêu tốn năng lượng.', 'nồng độ thấp đến nơn có nồng độ cao, đòi hỏi phải tiêu tốn năng lượng.'),
(6, 'DE0002', 'Câu 6 Điểm khác biệt trong cơ cấu GDP phân theo khu vực kinh tế của nhóm nước đang phát triển so với nhóm nước phát triển là:', 'Khu vực I có tỉ trọng rất thấp', 'Khu vực III có tỉ trọng rất cao', 'Khu vực I có tỉ trọng còn cao', 'Khu vực II có tỉ trọng rất cao', 'Khu vực I có tỉ trọng còn cao'),
(6, 'DE0003', 'Câu 6: Các loại tỉ lệ là:', 'Tỉ lệ thu nhỏ', 'Tỉ lệ phóng to', 'Tỉ lệ nguyên hình', 'Cả 3 đáp án trên', 'Cả 3 đáp án trên'),
(6, 'DE0004', 'Câu 6: Có mấy loại bản vẽ kĩ thuật?', '1', '2', '3', 'Có rất nhiều', 'Có rất nhiều'),
(6, 'DE0005', 'Câu 6. Người tối cổ đã tạo ra công cụ lao động như thế nào?', 'Lấy những mảnh đá, hòn cuội có sẵn trong tự nhiên để làm công cụ.', 'Ghè, đẽo một mặt mảnh đá hay hòn cuội.', 'Ghè đẽo, mài một mặt mảnh đá hay hòn cuội.', 'Ghè đẽo, mài cẩn thận hai mặt mảnh đá.', 'Ghè, đẽo một mặt mảnh đá hay hòn cuội.'),
(7, 'DE0001', 'Câu 7. Trong các phát biểu sau:\r\n(1) Lách vào kẽ đất hút nước và ion khoáng cho cây.\r\n(2) Bám vào kẽ đất làm cho cây đứng vững chắc.\r\n(3) Lách vào kẽ đất hở giúp cho rễ lấy được oxi để hô hấp.\r\n(4) Tế bào kéo dài, lách vào các kẽ đất làm cho bộ rễ lan rộng.\r\nCó bao nhiêu phát biểu đúng về vai trò của lông hút?', '1', '2', '3', '4', '1'),
(7, 'DE0002', 'Câu 7. Nguyên nhân cơ bản tạo nên sự khác biệt về cơ cấu GDP phân theo khu vực kinh tế giữa nhóm nước phát triển và đang phát triển là', 'Trình độ phát triển kinh tế\r\n', 'Sự phong phú về tài nguyên', 'Sự đa dạng về thành phần chủng tộc', 'Sự phong phú về nguồn lao động', 'Trình độ phát triển kinh tế'),
(7, 'DE0003', 'Câu 7: Nét liền mảnh thể hiện:', 'Đường kích thước', 'Đường giống', 'Đường gạch gạch trên mặt cắt', 'Cả 3 đáp án trên', 'Cả 3 đáp án trên'),
(7, 'DE0004', 'Câu 7: Giai đoạn 2 của thiết kế nhằm mục đích:', 'Xác định hình dạng sản phẩm', 'Xác định kết cấu sản phẩm', 'Xác định chức năng sản phẩm', 'Xác định hình dạng, kích thước, kết cấu, chức năng sản phẩm', 'Xác định hình dạng, kích thước, kết cấu, chức năng sản phẩm'),
(7, 'DE0005', 'Câu 7: Ý nào không phản ánh đúng công dụng của những chiếc rìu đá của Người tối cổ?', 'Chặt cây cối.', 'Dùng trực tiếp làm vũ khí tự vệ.', 'Tấn công các con thú để tạo ra thức ăn.', 'Dùng làm công cụ gieo hạt.', 'Dùng làm công cụ gieo hạt.'),
(8, 'DE0001', 'Câu 8. Trong rễ, bộ phận quan trọng nhất giúp cây hút nước và muối khoáng là', 'miền lông hút.', 'miền sinh trưởng.', 'miền chóp rễ.', 'miền trưởng thành.', 'miền lông hút.'),
(8, 'DE0002', 'Câu 8. Ở nhóm nước phát triển, người dân có tuổi thọ trung bình cao, nguyên nhân chủ yếu là do:', 'Môi trường sống thích hợp', 'Chất lượng cuộc sống cao', 'Nguồn gốc gen di truyền', 'Làm việc và nghỉ ngơi hợp lí', 'Chất lượng cuộc sống cao'),
(8, 'DE0003', 'Câu 8: Kích thước trên bản vẽ kĩ thuật có đơn vị: ', 'mm', 'dm', 'cm', 'Tùy từng bản vẽ', 'mm'),
(8, 'DE0004', 'Câu 8: Giai đoạn nào sau đây thuộc quá trình thiết kế?', 'Làm mô hình thử nghiệm, chế tạo thử', 'Lập hồ sơ kĩ thuật', 'Cả A và B đều đúng', 'Cả A và B đều sai', 'Cả A và B đều đúng'),
(8, 'DE0005', 'Câu 8: Phát minh quan trọng nhất, giúp cải thiện cuộc sống của Người tối cổ là', 'Biết chế tác công cụ lao động.', 'Biết cách tạo ra lửa.', 'Biết chế tác đồ gốm.', 'Biết trồng trọt và chăn nuôi.', 'Biết cách tạo ra lửa.'),
(9, 'DE0001', 'Câu 9. Trong các biện pháp sau:\r\n(1) Phơi ải đất, cày sâu, bừa kĩ.\r\n(2) Tưới nước đầy đủ và bón phân hữu cơ cho đất.\r\n(3) Giảm bón phân vô cơ và hữu cơ cho đất.\r\n(4) Vun gốc và xới đất cho cây.\r\nCó bao nhiêu biện pháp giúp cho bộ rễ cây phát triển?', '1', '2', '3', '4', '3'),
(9, 'DE0002', 'Câu 9. Châu lục có huổi thọ trung bình của người dân thấp nhất thế giới là', 'Châu Âu', 'Châu Á', 'Châu Mĩ', 'Châu Phi', 'Châu Phi'),
(9, 'DE0003', 'Câu 9: Phát biểu nào sau đây đúng:', 'Đường kích thước thẳng đứng, con số kích thước ghi bên phải', 'Đường kích thước nằm ngang, con số kích thước ghi bên trên', 'Đường kích thước nằm nghiêng, con số kích thước ghi bên dưới', 'Ghi kí hiệu R trước con số chỉ kích thước đường kính đường tròn', 'Đường kích thước nằm ngang, con số kích thước ghi bên trên'),
(9, 'DE0004', 'Câu 9: Vai trò của bản vẽ kĩ thuật đối với thiết kế là:', 'Đọc bản vẽ để thu thập thông tin liên quan đến đề tài thiết kế.', 'Vẽ các bản vẽ phác của sản phẩm khi lập phương án thiết kế để thể hiện ý tưởng thiết kế', 'Dùng các bản vẽ để trao đổi ý kiến với đồng nghiệp', 'Cả 3 đáp án trên', 'Cả 3 đáp án trên'),
(9, 'DE0005', 'Câu 9: Vai trò quan trọng nhất của lao động trong quá trình hình thành loài người là', 'Giúp cho đời sống vật chất và tinh thần của con người ngày càng ổn định và tiến bộ hơn.', 'Giúp con người từng bước khám phá, cải tạo thiên nhiên để phục vụ cuộc sống của mình.', 'Giúp con người tự cải biến, hoàn thiện mình,tạo nên bước nhảy vọt từ vượn thành người.', 'Giúp cho việc hình thành và cố kết mối quan hệ cộng đồng.', 'Giúp con người tự cải biến, hoàn thiện mình,tạo nên bước nhảy vọt từ vượn thành người.'),
(10, 'DE0001', 'Câu 10. Điều không đúng với sự hấp thụ thụ động các ion khoáng ở rễ là các ion khoáng', 'hòa tan trong nước và vào rễ theo dòng nước.', 'hút bám trên bề mặt của keo đất và trên bề mặt rễ, trao đổi với nhau khi có sự tiếp xúc giữa rễ và dung dịch đất (hút bám trao đổi).', 'thẩm thấu theo sự chênh lệch nồng độ từ cao đến thấp.', 'khếch tán theo sự chênh lệch nồng độ từ cao đến thấp.', 'thẩm thấu theo sự chênh lệch nồng độ từ cao đến thấp.'),
(10, 'DE0002', 'Câu 10: Đặc trưng của cuộc cách mạng khoa học và công nghệ hiện đại là xuất hiện và phát triển nhanh chóng', 'Công nghiệp khai thác', 'Công nghiệp dệ may', 'Công nghệ cao', 'Công nghiệp cơ khí', 'Công nghệ cao'),
(10, 'DE0003', 'Câu 10: Phát biểu nào sau đây sai?', 'Nét liền mảnh biểu diễn đường giống', 'Nét liền đậm biểu diễn đường bao thấy', 'Nét gạch chấm mảnh biểu diễn đường tâm', 'Nét lượn sóng biểu diễn đường giống', 'Nét lượn sóng biểu diễn đường giống'),
(10, 'DE0004', 'Câu 10: Giai đoạn cuối của quá trình thiết kế là:', 'Xác định đề tài thiết kế', 'Lập hồ sơ kĩ thuật', 'Làm mô hình thử nghiệm', 'Chế tạo thử', 'Lập hồ sơ kĩ thuật'),
(10, 'DE0005', 'Câu 10: Hợp quần xã hội đầu tiên của con người gọi là', 'Bầy người nguyên thủy.', 'Thị tộc', 'Bộ lạc', 'Xã hội loài người sơ khai.', 'Bầy người nguyên thủy.'),
(11, 'DE0005', 'Câu 11: Các nhà khảo cổ coi thời đá mới là một cuộc cách mạng vì', 'Thời kì này xuất hiện những loại hình công cụ mới.', 'Con người biết đan lưới đánh cá, biết làm đồ gốm.', 'Có những thay đổi căn bản trong kĩ thuật chế tác công cụ, làm xuất hiện những loại hình công cụ mới;có sự thay đổi lớn lao trong đời sống và tổ chức xã hội.', 'Con người có những sáng tạo lớn lao, sống tốt hơn, vui hơn.', 'Có những thay đổi căn bản trong kĩ thuật chế tác công cụ, làm xuất hiện những loại hình công cụ mới;có sự thay đổi lớn lao trong đời sống và tổ chức xã hội.'),
(12, 'DE0005', 'Câu 12: Ý không phản ánh đúng những thay đổi trong đời sống con người thời đá mới là', 'Chuyển từ nền kinh tế thu lượm tự nhiên sang nền kinh tế sản xuất (biết trồng trọt và chăn nuôi).', 'Biết làm quần áo để mặc, làm nhà để ở, làm đồ trang sức bằng xương và đá.', 'Biết sáng tạo trong cuộc sống tinh thần.', 'Bắt đầu hình thành những tín ngưỡng, tôn giáo nguyên thủy.', 'Bắt đầu hình thành những tín ngưỡng, tôn giáo nguyên thủy.'),
(13, 'DE0005', 'Câu 13: Bước nhảy vọt đầu tien trong quá trình tiến hóa từ vượn thành người là', 'Từ vượn thành vượn cổ.', 'Từ vượn thành Người tối cổ.', 'Từ Người tối cổ sang Người tinh khôn.', 'Từ giai đoạn đá cũ sang đá mới', 'Từ vượn thành Người tối cổ.'),
(14, 'DE0005', 'Câu 14: Ý không phản ánh đúng đặc điểm của hợp quần xã hội đầu tiên của con người là ', 'Có người đứng đầu.', 'Có phân công lao động giữa nam và nữ.', 'Sống quây quần theo quan hệ ruột thịt trong các hang động, mái đá, túp lều.', 'Có sự phân hóa giàu nghèo.', 'Có sự phân hóa giàu nghèo.'),
(15, 'DE0005', 'Câu 15: Thành ngữ nào phản ánh đúng nhất tình trạng đời sống của Người tối cổ', 'Ăn lông ở lỗ.', 'Ăn sống nuốt tươi.', 'Nay đây mai đó.', 'Man di mọi dợ.', 'Ăn lông ở lỗ.'),
(16, 'DE0005', 'Câu 16: Đến thời điểm nào thì Người tối cổ trở thành Người tinh khôn?', 'Đã đi dứng thẳng bằng hai chân, hai tay đã được giải phóng.', 'Khi loại bỏ hết dấu tích vượn trên cơ thể.', 'Biết chế tác công cụ lao động.', 'Biết săn thú, hái quả để làm thức ăn.', 'Khi loại bỏ hết dấu tích vượn trên cơ thể.'),
(17, 'DE0005', 'Câu 17: Ý nào không phản ánh đúng về cấu tạo của Người tinh khôn', 'Xương cốt nhỏ hơn Người tối cổ.', 'Đôi bàn tay nhỏ, khéo léo, các ngón tay linh hoạt.', 'Hộp sọ đã lớn hơn, hình thành trung tâm phát tiếng nói trong não.', 'Cơ thể gọn và linh hoạt, thích hợp với các hoạt động phức tạp.', 'Hộp sọ đã lớn hơn, hình thành trung tâm phát tiếng nói trong não.'),
(18, 'DE0005', 'Câu 18: Màu da nào không được xác định là một chủng tộc được hình thành từ thời nguyên thủy', 'Vàng', 'Đen', 'Trắng', 'Đỏ', 'Đỏ'),
(19, 'DE0005', 'Câu 19: Có sự khác nhau về màu da giữa các chủng tộc là do đâu?', 'Sự khác nhau về trình độ hiểu biết.', 'Sự thích ứng lâu dài của con người với điều kiện tự nhiên.', 'Do di truyền.', 'Điều kiện sống và hiểu biết của con người khác nhau.', 'Sự thích ứng lâu dài của con người với điều kiện tự nhiên.'),
(20, 'DE0005', 'Câu 20: Trong chế tác công cụ lao động, Người tinh khôn đã biết làm gì?', 'Lấy những mảnh đá, hòn cuội có sẵn trong tự nhiên để làm công cụ.', 'Ghè, đẽo một mảnh đá hoặc hòn cuội.', 'Ghè đẽo hai rìa của một mặt mảnh đá; chế tạo lao từ xương cá, cành cây được mài hoặc đẽo nhọn đầu.', 'Ghè đẽo, mài cẩn thận hai mặt mảnh đá.', 'Ghè đẽo hai rìa của một mặt mảnh đá; chế tạo lao từ xương cá, cành cây được mài hoặc đẽo nhọn đầu.');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `diem`
--

CREATE TABLE `diem` (
  `maDeThi` varchar(6) COLLATE utf8_bin NOT NULL,
  `userName` varchar(100) COLLATE utf8_bin NOT NULL,
  `diem` float NOT NULL,
  `thuHang` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `diem`
--

INSERT INTO `diem` (`maDeThi`, `userName`, `diem`, `thuHang`) VALUES
('DE0001', 'nguyentuyetnhung09@gmail.com', 10, 1),
('DE0002', 'nguyennguyen@gmail.com', 7, 1),
('DE0003', 'hoangchuong06022000@gmail.com', 8, 1),
('DE0004', 'lynhathao10@gmail.com', 9, 1),
('DE0005', 'lengocyen19@gmail.com', 9.5, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `madethi`
--

CREATE TABLE `dethi` (
  `maDeThi` varchar(6) COLLATE utf8_bin NOT NULL,
  `tenDeThi` varchar(200) COLLATE utf8_bin NOT NULL,
  `soCauHoi` int(3) NOT NULL,
  `thoiGianThi` int(3) NOT NULL,
  `soLuotThi` int(3) NOT NULL,
  `userName` varchar(100) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `madethi`
--

INSERT INTO `dethi` (`maDeThi`, `tenDeThi`, `soCauHoi`, `thoiGianThi`, `soLuotThi`, `userName`) VALUES
('DE0001', 'Sinh học 11 Bài 1: Sự hấp thụ nước và muối khoáng ở rễ', 10, 10, 0, 'hoangchuong06022000@gmail.com'),
('DE0002', 'Địa Lí 11 Bài 1: Sự tương phản về trình độ phát triển kinh tế - xã hội (phần 1)', 10, 10, 0, 'lengocyen19@gmail.com'),
('DE0003', 'Công nghệ 11 Bài 1: Tiêu chuẩn trình bày bản vẽ kĩ thuật', 10, 10, 0, 'lynhathao10@gmail.com'),
('DE0004', 'Công nghệ 11 Bài 8: Thiết kế và bản vẽ kĩ thuật', 10, 10, 0, 'nguyennguyen@gmail.com'),
('DE0005', 'Lịch Sử 10 Bài 1: Sự xuất hiện loài người và bầy người nguyên thủy', 20, 20, 0, 'nguyentuyetnhung09@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `userName` varchar(100) COLLATE utf8_bin NOT NULL,
  `password` varchar(50) COLLATE utf8_bin NOT NULL,
  `hoTen` varchar(100) COLLATE utf8_bin NOT NULL,
  `gioiTinh` int(1) NOT NULL,
  `ngSinh` date NOT NULL,
  `trangThai` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`userName`, `password`, `hoTen`, `gioiTinh`, `ngSinh`, `trangThai`) VALUES
('hoangchuong06022000@gmail.com', '1f5db3015992f698e9bd6ab0f3c08f69', 'Văn Hoàng Chương', 0, '2000-02-06', 1),
('lengocyen19@gmail.com', '480f9a6abd4c51c88998c547fb544513', 'Lê Ngọc Yến', 1, '2000-10-27', 1),
('lynhathao10@gmail.com', 'f9432528d14e67cd86155ac7c314d20c', 'Lý Nhật Hào', 0, '2000-09-28', 1),
('nguyennguyen@gmail.com', '66c6883a896b36c3734abccad05d84e1', 'Hoàng Văn Nguyên', 0, '2000-12-01', 1),
('nguyentuyetnhung09@gmail.com', '38a54833285327624997eb938c0361d3', 'Nguyễn Tuyết Nhung', 1, '2000-06-16', 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `cauhoi`
--
ALTER TABLE `cauhoi`
  ADD PRIMARY KEY (`STT`,`maDeThi`),
  ADD KEY `maDeThi` (`maDeThi`);

--
-- Chỉ mục cho bảng `diem`
--
ALTER TABLE `diem`
  ADD PRIMARY KEY (`maDeThi`,`userName`),
  ADD KEY `maDeThi` (`maDeThi`),
  ADD KEY `userName` (`userName`);

--
-- Chỉ mục cho bảng `madethi`
--
ALTER TABLE `dethi`
  ADD PRIMARY KEY (`maDeThi`),
  ADD KEY `userName` (`userName`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userName`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `cauhoi`
--
ALTER TABLE `cauhoi`
  ADD CONSTRAINT `cauhoi_ibfk_1` FOREIGN KEY (`maDeThi`) REFERENCES `dethi` (`maDeThi`);

--
-- Các ràng buộc cho bảng `diem`
--
ALTER TABLE `diem`
  ADD CONSTRAINT `diem_ibfk_1` FOREIGN KEY (`maDeThi`) REFERENCES `dethi` (`maDeThi`),
  ADD CONSTRAINT `diem_ibfk_2` FOREIGN KEY (`userName`) REFERENCES `user` (`userName`);

--
-- Các ràng buộc cho bảng `madethi`
--
ALTER TABLE `dethi`
  ADD CONSTRAINT `madethi_ibfk_1` FOREIGN KEY (`userName`) REFERENCES `user` (`userName`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
