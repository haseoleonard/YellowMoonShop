# [LAB231]-[Lab Java Web]-[FPT University]-[NghiaDHSE140362] [YellowMoonShop](https://github.com/haseoleonard/YellowMoonShop/)
## Yellow Moon : Moon Cake Shop

The requirements is inside the PDF.

All the library used inside this project is inside the /libs directory.

### Usage Guide
* Setting Up DataSource Connection information for Database connection in web/META-INF/context.xml
* Setting up value for Project Initialize in web/WEB-INF/web.xml:
  * IMAGE_UPLOAD_DIR: Directory for saving newly Uploaded Product's Image.
  * MOMO_ACCESS_KEY: get this on [Momo Bussiness page](https://business.momo.vn)
  * MOMO_PARTNER_CODE: get this on [Momo Bussiness page](https://business.momo.vn)
  * MOMO_SECRET_KEY: HMAC SHA-256 Signature encrypting key for momo. Get this on [Momo Bussiness page](https://business.momo.vn)
  * MOMO_PORTAL: URL to MoMo Payment Portal
  * MOMO_RETURN_URL: the URL for Momo to redirect user after finished transactions
  * MOMO_NOTIFY_URL:
  * GOOGLE_CLIENT_ID: clientID to use with GoogleAPI
* Log4j Log File Location can be changed in /web/WEB-INF/log4j.properties:
  * log4j.appender.INFOFILELOG.File=C:/Logs/Log4jYellowMoonInfo.log - Entire Access Info Log File
  * log4j.appender.ERRORFILELOG.File=C:/Logs/Log4jYellowMoonkError.log - Error Log File

### Author
  Haseo Leonard - Dao Huu Nghia - SE140362

### Mentor - Instructor
* Mr. Doan Nguyen Thanh Hoa
* Mr. Kieu Trong Khanh

### Supporting Team Member
* Mr. Bùi Anh Huy
* Mr. Bùi Nguyễn Hoàng Long
* Mr. Trần Phan Trường Thịnh

### Editor used
* Netbeans 8.2
* MS-SQL Management Studio
* Idea IntelliJ Ultimate

### Used language
* SQL
* Java
* HTML
* CSS
* JS

### Implemented technology
* Java JDK14
* Bootstrap 4
* JQuery
* MoMo API
* Login with Google API
* Log4j 1.2.17
* MSSQL JDBC 8.2.0
* Apache Commons Upload

© 2020 HaseoLeonard | facebook.com/okaminoizanagi
