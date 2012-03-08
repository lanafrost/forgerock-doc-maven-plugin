# ForgeRock doc-build-plugin

Today the configurations for ForgeRock core documentation are maintained
in sync by copy/paste.

A better solution would centralize configuration, leaving only the source
files and a small amount of configuration per core documentation project.

    <build>
      <plugins>
       <plugin>
        <groupId>org.forgerock.commons</groupId>
        <artifactId>doc-build-plugin</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <inherited>false</inherited>
        <configuration>
         <projectName>OpenAM</projectName>
         <googleAnalyticsId>UA-23412190-7</googleAnalyticsId>
        </configuration>
        <executions>
         <execution>
          <id>build-doc</id>
          <phase>pre-site</phase>
          <goals>
           <goal>build</goal>
          </goals>
         </execution>
         <execution>
          <id>layout-doc</id>
          <phase>site</phase>
          <goals>
           <goal>layout</goal>
          </goals>
         </execution>
        </executions>
       </plugin>
      </plugins>
    </build>

More to come...

* * *
This work is licensed under the Creative Commons
Attribution-NonCommercial-NoDerivs 3.0 Unported License.
To view a copy of this license, visit
<http://creativecommons.org/licenses/by-nc-nd/3.0/>
or send a letter to Creative Commons, 444 Castro Street,
Suite 900, Mountain View, California, 94041, USA.

Copyright 2012 ForgeRock AS
