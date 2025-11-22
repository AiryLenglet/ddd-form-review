db.createCollection("form");
db.form.createIndex(
           {
                "caseId": 1,
                "version": -1
           },
           {
                unique: true
           }
);