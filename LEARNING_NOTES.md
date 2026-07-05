Repositories are interface because they can implement implicit method of jpa repositories. its efficient and timely than creating classes and initialising methods are each respective entity
JpaRepo provides dynamic functionability. implicit methods like save(), delete(), findAll(), findById() and this time I created custom methods. 
important thing to note is that those custom functions are crude and must have respective fields in their respective entity.
Spring derives queries from method names using spring Jpa repositories.
Optional is used so that we can bypass nullpointer exception and unwanted exception. 
boolean is primitive class cannot return null while Boolean is wrapper class and can store null
nested properties like findbyAccountUserEmail() work by object Relational Mapping(ORM) for it to work the field email must be defined within object user which in terun must be fedined within account.
object relationship works within java while foreign keys uses hibernate to talk to database.