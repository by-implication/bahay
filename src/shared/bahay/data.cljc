(ns bahay.data)

(def roles
  {:cof {:role/id :cof :role/name "Co-Founder"}
   :dev {:role/id :dev :role/name "Developer"}
   :des {:role/id :des :role/name "Designer"}
   :ban {:role/id :ban :role/name "Business Analyst"}})

(def people
  [{:person/id 0
    :person/name {:nick "Levi"
                  :first "Levi"
                  :Last "Tan Ong"}
    :person/roles [(roles :cof) (roles :dev) (roles :des)]
    :person/link "http://github.com/levitanong"
    :person/image-url "data/people/Levi.png"
    :person/writeup "Lorem Ipsum Dolor"}
   {:person/id 1
    :person/name "Dude McDudeman"
    :person/roles [(roles :cof) (roles :dev)]}])
