 alter table acct_role_authority 
        drop constraint FKAE243466DE3FB930;

    alter table acct_role_authority 
        drop constraint FKAE2434663FE97564;

    alter table acct_user_role 
        drop constraint FKFE85CB3EDE3FB930;

    alter table acct_user_role drop constraint FKFE85CB3E836A7D10;

    alter table marriage drop constraint fk_marriage_husband;
    alter table marriage drop constraint fk_marriage_wife;
    alter table marriage drop constraint uk_marriage;
    alter table marriage drop constraint fk_children_marriage;
    alter table marriage drop constraint fk_children_people;
    alter table marriage drop constraint uk_children;
    