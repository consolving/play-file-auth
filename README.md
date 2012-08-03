play-file-auth
==============

This Module supports authentication of Play! Applications agains Unix Auth Files (e.g. Apache htusers)

This Plugin works for all Auth Files with the following syntax:


    user1:passwordhash
    user1:passwordhash:...:...:

    group:user1 user2
    group:x:...:user1,user2


The Hash Algorithm has to be either MD5 Crypt or MD5 Apache Crypt.

    $1$...
    $apr1$...