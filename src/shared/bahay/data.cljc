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

(def services
  {:ui {:service/id :ui :service/name "UI Design"}
   :dev {:service/id :dev :service/name "Development"}
   :dir {:service/id :dir :service/name "Business Direction"}
   :big {:service/id :big :service/name "Big Data"}})

(def projects
  [{:project/id :sakay
    :project/name "Sakay"
    :project/ownership :self
    :project/services [(services :ui) (services :dev) (services :big)]
    :project/featured true}
   {:project/id :openrecon
    :project/name "Open Reconstruction"
    :project/ownership :client
    :project/services [(services :ui) (services :dev)]}
   {:project/id :storylark
    :project/name "Storylark"}
   #_{:project/id :wildfire
    :project/name "Wildfire"}])
