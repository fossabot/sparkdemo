package com.gehc.apps.fmk.sparkdemo;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "json:target/cucumber.json", features = "classpath:features/")
public class PostServiceTest{

}