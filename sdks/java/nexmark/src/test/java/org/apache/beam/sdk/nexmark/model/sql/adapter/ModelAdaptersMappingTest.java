/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.beam.sdk.nexmark.model.sql.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.apache.beam.sdk.nexmark.model.Auction;
import org.apache.beam.sdk.nexmark.model.Bid;
import org.apache.beam.sdk.nexmark.model.Person;
import org.apache.beam.sdk.schemas.Schema;
import org.joda.time.DateTime;
import org.junit.Test;

/** Unit tests for {@link ModelAdaptersMapping}. */
public class ModelAdaptersMappingTest {

  private static final Person PERSON =
      new Person(3L, "name", "email", "cc", "city", "state", 329823L, "extra");

  private static final Schema PERSON_ROW_TYPE =
      Schema.builder()
          .addInt64Field("id")
          .addStringField("name")
          .addStringField("emailAddress")
          .addStringField("creditCard")
          .addStringField("city")
          .addStringField("state")
          .addDateTimeField("dateTime")
          .addStringField("extra")
          .build();

  private static final Bid BID = new Bid(5L, 3L, 123123L, 43234234L, "extra2");

  private static final Schema BID_ROW_TYPE =
      Schema.builder()
          .addInt64Field("auction")
          .addInt64Field("bidder")
          .addInt64Field("price")
          .addDateTimeField("dateTime")
          .addStringField("extra")
          .build();

  private static final Auction AUCTION =
      new Auction(5L, "item", "desc", 342L, 321L, 3423342L, 2349234L, 3L, 1L, "extra3");

  private static final Schema AUCTION_ROW_TYPE =
      Schema.builder()
          .addInt64Field("id")
          .addStringField("itemName")
          .addStringField("description")
          .addInt64Field("initialBid")
          .addInt64Field("reserve")
          .addDateTimeField("dateTime")
          .addDateTimeField("expires")
          .addInt64Field("seller")
          .addInt64Field("category")
          .addStringField("extra")
          .build();

  @Test
  public void hasAdaptersForSupportedModels() throws Exception {
    assertTrue(ModelAdaptersMapping.ADAPTERS.containsKey(Bid.class));
    assertTrue(ModelAdaptersMapping.ADAPTERS.containsKey(Person.class));
    assertTrue(ModelAdaptersMapping.ADAPTERS.containsKey(Auction.class));

    assertNotNull(ModelAdaptersMapping.ADAPTERS.get(Bid.class));
    assertNotNull(ModelAdaptersMapping.ADAPTERS.get(Person.class));
    assertNotNull(ModelAdaptersMapping.ADAPTERS.get(Auction.class));
  }

  @Test
  public void testBidAdapterRecordType() {
    ModelFieldsAdapter<Person> adapter = ModelAdaptersMapping.ADAPTERS.get(Bid.class);

    Schema bidSchema = adapter.getSchema();

    assertEquals(BID_ROW_TYPE, bidSchema);
  }

  @Test
  public void testPersonAdapterRecordType() {
    ModelFieldsAdapter<Person> adapter = ModelAdaptersMapping.ADAPTERS.get(Person.class);

    Schema personSchema = adapter.getSchema();

    assertEquals(PERSON_ROW_TYPE, personSchema);
  }

  @Test
  public void testAuctionAdapterRecordType() {
    ModelFieldsAdapter<Person> adapter = ModelAdaptersMapping.ADAPTERS.get(Auction.class);

    Schema auctionSchema = adapter.getSchema();

    assertEquals(AUCTION_ROW_TYPE, auctionSchema);
  }

  @Test
  public void testPersonAdapterGetsFieldValues() throws Exception {
    ModelFieldsAdapter<Person> adapter = ModelAdaptersMapping.ADAPTERS.get(Person.class);
    List<Object> values = adapter.getFieldsValues(PERSON);
    assertEquals(PERSON.id, values.get(0));
    assertEquals(PERSON.name, values.get(1));
    assertEquals(PERSON.emailAddress, values.get(2));
    assertEquals(PERSON.creditCard, values.get(3));
    assertEquals(PERSON.city, values.get(4));
    assertEquals(PERSON.state, values.get(5));
    assertEquals(new DateTime(PERSON.dateTime), values.get(6));
    assertEquals(PERSON.extra, values.get(7));
  }

  @Test
  public void testBidAdapterGetsFieldValues() throws Exception {
    ModelFieldsAdapter<Bid> adapter = ModelAdaptersMapping.ADAPTERS.get(Bid.class);
    List<Object> values = adapter.getFieldsValues(BID);
    assertEquals(BID.auction, values.get(0));
    assertEquals(BID.bidder, values.get(1));
    assertEquals(BID.price, values.get(2));
    assertEquals(new DateTime(BID.dateTime), values.get(3));
    assertEquals(BID.extra, values.get(4));
  }

  @Test
  public void testAuctionAdapterGetsFieldValues() throws Exception {
    ModelFieldsAdapter<Auction> adapter = ModelAdaptersMapping.ADAPTERS.get(Auction.class);
    List<Object> values = adapter.getFieldsValues(AUCTION);
    assertEquals(AUCTION.id, values.get(0));
    assertEquals(AUCTION.itemName, values.get(1));
    assertEquals(AUCTION.description, values.get(2));
    assertEquals(AUCTION.initialBid, values.get(3));
    assertEquals(AUCTION.reserve, values.get(4));
    assertEquals(new DateTime(AUCTION.dateTime), values.get(5));
    assertEquals(new DateTime(AUCTION.expires), values.get(6));
    assertEquals(AUCTION.seller, values.get(7));
    assertEquals(AUCTION.category, values.get(8));
    assertEquals(AUCTION.extra, values.get(9));
  }
}
