## HAPIModule
A wrapper for the [HAPI (Hl7 API) module](http://hl7api.sourceforge.net/). **HAPI** is an open-source, object-oriented HL7 2.x parser for Java.
[HL7]( http://hl7.org ) is a standard messaging protocol/specification for the exchange of healthcare information.

Hl7 Corresponds to the Application Layer (layer 7) of the OSI communication layer levels, is event-driven, and independent of transport protocols 
(i.e. FTP, TCP/IP, LLP etc)

Currently the HAPIModule only supports the following message types:
- All ADT message types (although only tested on A04 and A08)
- ORU^R01

####Usage:
Add the following repository to your pom file


        <repository>
            <id>HAPIModule-mvn-repository</id>
            <url>https://raw.github.com/tedb19/HAPIModule/mvn-repository/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
        

Add the following dependency to your pom file:


        <dependency>
            <groupId>hapimodule.core</groupId>
            <artifactId>HapiModuleV2</artifactId>
            <version>2.0</version>
            <exclusions>
                <exclusion>
                    <artifactId>log4j</artifactId>
                    <groupId>log4j</groupId>
                </exclusion>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        

For example use-cases, look at the main method
