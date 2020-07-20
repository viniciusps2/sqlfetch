package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"strings"
	// _ "github.com/denisenkom/go-mssqldb"
)

type Table struct {
	Name        string
	PrimaryKeys []string
}

type JoinKey struct {
	Left  string
	Right string
}

type Join struct {
	LeftTable  string
	RightTable string
	Keys       []JoinKey
}

type FetchKey struct {
	Name  string
	Value string
}

type Fetch struct {
	Table string
	Keys  []FetchKey
}

type Config struct {
	Tables []Table
	Joins  []Join
	Fetch  Fetch
}

type Query struct {
	Select string
	From   string
	Joins  string
	Where  string
}

func main() {
	config := ReadJson()
	log.Println(config)
	// parsing.LoadAllAssociations(schema, "configuration", "1")
}

func ReadJson() Config {
	txt, err := ioutil.ReadFile("./config.json")
	if err != nil {
		log.Fatal(err)
	}

	// log.Println(string(txt))
	var config Config
	if err := json.Unmarshal(txt, &config); err != nil {
		log.Fatal(err)
	}
	return config
}

func BuildQuery(config *Config) string {
	selectList := make([]string, 0)
	for _, table := range config.Tables {
		for _, key := range table.PrimaryKeys {
			field := fmt.Sprintf("%s.%s AS %s@%s", table.Name, key, table.Name, key)
			selectList = append(selectList, field)
		}
	}
	keysList := make([]string, 0)
	for _, key := range config.Fetch.Keys {
		condition := fmt.Sprintf("%s.%s = '%s'", config.Fetch.Table, key.Name, key.Value)
		keysList = append(keysList, condition)
	}
	joinList := make([]string, 0)
	for _, join := range config.Joins {
		whereList := make([]string, 0)
		for _, key := range join.Keys {
			condition := fmt.Sprintf("%s.%s = %s.%s", join.LeftTable, key.Left, join.RightTable, key.Right)
			whereList = append(whereList, condition)
		}
		joinText := fmt.Sprintf("LEFT JOIN %s ON %s", join.RightTable, strings.Join(whereList, " AND "))
		joinList = append(joinList, joinText)
	}
	query := &Query{
		Select: strings.Join(selectList, ", "),
		From:   config.Fetch.Table,
		Joins:  strings.Join(joinList, " "),
		Where:  strings.Join(keysList, " AND "),
	}
	return fmt.Sprintf("SELECT %s FROM %s %s WHERE %s", query.Select, query.From, query.Joins, query.Where)
}

// SELECT id AS 'configuration.id' FROM configuration WHERE id = %s
