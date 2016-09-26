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
    :person/writeup "Lorem Ipsum Dolor"}])

(def projects
  [{:project/id :badger
    :project/name "Budget Badger"}
   {:project/id :openrecon
    :project/name "Open Reconsutrction"}
   {:project/id :sakay
    :project/name "Sakay"}
   {:project/id :sari
    :project/name "Sari"}
   {:project/id :storylark
    :project/name "Storylark"}
   {:project/id :torch
    :project/name "Torch"}
   {:project/id :website
    :project/name "By Implication"}
   {:project/id :wildfire
    :project/name "Wildfire"}])
