(ns bahay.data)

(def asset-url
  "http://assets.byimplication.com/site/")

(def roles
  {:cof {:role/id :cof :role/name "Co-Founder"}
   :dev {:role/id :dev :role/name "Developer"}
   :des {:role/id :des :role/name "Designer"}
   :biz {:role/id :ban :role/name "Business Analyst"}})

(def people
  (->>
    [#:person{:nick-name "Levi"
              :display-name "Levi Tan Ong"
              :roles [:cof :dev :des]
              :link "http://github.com/levitanong"}
     #:person{:nick-name "Phi"
              :display-name "Philip Cheang"
              :roles [:cof :dev :des]
              :link "http://phi.ph"}
     #:person{:nick-name "Rodz"
              :display-name "Rodrick Tan"
              :roles [:cof :biz]}
     #:person{:nick-name "Kenneth"
              :display-name "Kenneth Yu"
              :roles [:cof :biz]
              :link "http://twitter.com/kennethgyu"}
     #:person{:nick-name "Wil"
              :display-name "Wilhansen Li"
              :roles [:cof :dev]
              :link "http://wilhansen.li"}
     #:person{:nick-name "Pepe"
              :display-name "Pepe Bawagan"
              :roles [:dev]
              :link "http://syk0saje.com"}
     #:person{:nick-name "Albert"
              :display-name "Albert Dizon"
              :roles [:dev]}
     #:person{:nick-name "Sesky"
              :display-name "Jonathan Sescon"
              :roles [:dev]
              :link "https://github.com/jrsescon"}
     #:person{:nick-name "Patsy"
              :display-name "Patricia Lascano"
              :roles [:des]}
     #:person{:nick-name "Alvin"
              :display-name "Alvin Dumalus"
              :roles [:dev]
              :link "https://github.com/alvinfrancis"}
     #:person{:nick-name "Thomas"
              :display-name "Thomas Dy"
              :roles [:dev]
              :link "http://pleasantprogrammer.com"}
     #:person{:nick-name "Jim"
              :display-name "James Choa"
              :roles [:dev]
              :link "https://github.com/trigger-happy"}
     #:person{:nick-name "Enzo"
              :display-name "Lorenzo Vergara"
              :roles [:dev]}
     #:person{:nick-name "J"
              :display-name "John Ugalino"
              :roles [:dev]}]
    (map-indexed
      (fn [index person]
        (-> person
          (assoc
            :person/id index
            :person/image-url (str asset-url
                                "people/"
                                (:person/nick-name person)
                                ".png"))
          (update :person/roles (partial mapv roles)))))
    (vec)))

(def services
  {:ui {:service/id :ui :service/label "UI/UX"}
   :dev {:service/id :dev :service/label "Development"}
   :dir {:service/id :dir :service/label "Business Direction"}
   :big {:service/id :big :service/label "Big Data"}})

(def projects
  [{:project/id :sakay
    :project/label "Sakay"
    :project/ownership :in-house
    :project/services [(services :ui)
                       (services :dev)
                       (services :big)]
    :project/featured true
    :project/accent "#2a6d45"
    :project/image-url (str asset-url "projects/sakay/sakay-timedate-mockup.jpg")}
   {:project/id :openrecon
    :project/label "Open Reconstruction"
    :project/ownership :client
    :project/services [(services :ui) (services :dev)]}
   {:project/id :storylark
    :project/label "Storylark"
    :project/ownership :in-house
    :project/services [(services :ui) (services :dev)]}
   #_{:project/id :wildfire
    :project/name "Wildfire"}])
