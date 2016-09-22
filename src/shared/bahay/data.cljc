(ns bahay.data)

(def roles
  {0 {:role/id 0 :role/name "Co-Founder"}
   1 {:role/id 1 :role/name "Developer"}
   2 {:role/id 2 :role/name "Designer"}
   3 {:role/id 3 :role/name "Business Analyst"}})

(def people
  [{:person/id 0
    :person/name {:nick "Levi"
                  :first "Levi"
                  :Last "Tan Ong"}
    :person/roles [(get roles 0) (get roles 1) (get roles 2)]
    :person/link "http://github.com/levitanong"
    :person/writeup "Lorem Ipsum Dolor"}])
