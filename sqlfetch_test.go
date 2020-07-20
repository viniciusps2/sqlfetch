package main

import (
	"log"
	"testing"
)

func TestAbs(t *testing.T) {
	got := 1
	if got != 1 {
		t.Errorf("Abs(-1) = %d; want 1", got)
	}
}

func TestReadJson(t *testing.T) {
	got := ReadJson()
	log.Println(got)
}

func TestBuildQueryForOneTable(t *testing.T) {
	config := &Config{
		Tables: []Table{
			{
				Name:        "configuration",
				PrimaryKeys: []string{"id"},
			},
		},
		Fetch: Fetch{
			Table: "configuration",
			Keys: []FetchKey{
				{Name: "id", Value: "1"},
			},
		},
	}
	got := BuildQuery(config)
	expected := "SELECT configuration.id AS configuration@id FROM configuration WHERE configuration.id = '1'"
	if got != expected {
		t.Errorf("query result:\n %s\nwant:\n %s", got, expected)
	}
}

func TestBuildQueryForTwoTables(t *testing.T) {
	config := &Config{
		Tables: []Table{
			{
				Name:        "configuration",
				PrimaryKeys: []string{"id"},
			},
		},
		Joins: []Join{
			{
				LeftTable:  "configuration",
				RightTable: "feature_category",
				Keys: []JoinKey{
					{
						Left:  "id",
						Right: "configurationid",
					},
				},
			},
		},
		Fetch: Fetch{
			Table: "configuration",
			Keys: []FetchKey{
				{Name: "id", Value: "1"},
			},
		},
	}
	got := BuildQuery(config)
	expected := "SELECT configuration.id AS configuration@id FROM configuration LEFT JOIN feature_category ON feature_category.configurationid = configuration.id WHERE configuration.id = '1'"
	if got != expected {
		t.Errorf("query result:\n %s\nwant:\n %s", got, expected)
	}
}
