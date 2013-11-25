alter table marriage add constraint fk_marriage_husband foreign key (husband_id) references people (id);        
    alter table marriage add constraint fk_marriage_wife foreign key (wife_id) references people (id);    
    alter table marriage add constraint uk_marriage unique key (husband_id, wife_id);
    
    alter table children add constraint fk_children_marriage foreign key (marriage_id) references marriage(id);
    alter table children add constraint fk_children_people foreign key (people_id) references people(id);    
    alter table children add constraint uk_children unique key (marriage_id, people_id);
    
    alter table acct_role_authority 
        add constraint FKAE243466DE3FB930 
        foreign key (role_id) 
        references acct_role (id);

    alter table acct_role_authority 
        add constraint FKAE2434663FE97564 
        foreign key (authority_id) 
        references acct_authority (id);

    alter table acct_user_role 
        add constraint FKFE85CB3EDE3FB930 
        foreign key (role_id) 
        references acct_role (id);

    alter table acct_user_role 
        add constraint FKFE85CB3E836A7D10 
        foreign key (user_id) 
        references acct_user (id);
