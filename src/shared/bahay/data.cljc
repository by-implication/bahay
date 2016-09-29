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
  {:ui {:service/id :ui :service/label "UI Design"}
   :dev {:service/id :dev :service/label "Web Development"}
   :dir {:service/id :dir :service/label "Business Direction"}
   :big {:service/id :big :service/label "Big Data"}
   :ios {:service/id :ios :service/label "iOS Development"}
   :and {:service/id :and :service/label "Android Development"}})

(def projects
  [{:project/id :sakay
    :project/label "Sakay"
    :project/ownership :self
    :project/services [(services :ui)
                       (services :dev)
                       (services :big)
                       (services :ios)
                       (services :and)]
    :project/featured true}
   {:project/id :openrecon
    :project/label "Open Reconstruction"
    :project/ownership :client
    :project/services [(services :ui) (services :dev)]}
   {:project/id :storylark
    :project/label "Storylark"
    :project/ownership :self}
   #_{:project/id :wildfire
    :project/name "Wildfire"}])
