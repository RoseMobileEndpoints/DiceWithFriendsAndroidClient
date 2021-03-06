/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-07-22 21:53:01 UTC)
 * on 2014-08-13 at 14:44:39 UTC 
 * Modify at your own risk.
 */

package com.appspot.dice_with_friends.dicewithfriends.model;

/**
 * Model definition for Game.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the dicewithfriends. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Game extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("creator_key")
  private java.lang.String creatorKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("creator_scores") @com.google.api.client.json.JsonString
  private java.util.List<java.lang.Long> creatorScores;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String entityKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("invitee_key")
  private java.lang.String inviteeKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("invitee_scores") @com.google.api.client.json.JsonString
  private java.util.List<java.lang.Long> inviteeScores;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("is_complete")
  private java.lang.Boolean isComplete;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("is_solo")
  private java.lang.Boolean isSolo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("last_touch_date_time")
  private java.lang.String lastTouchDateTime;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCreatorKey() {
    return creatorKey;
  }

  /**
   * @param creatorKey creatorKey or {@code null} for none
   */
  public Game setCreatorKey(java.lang.String creatorKey) {
    this.creatorKey = creatorKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Long> getCreatorScores() {
    return creatorScores;
  }

  /**
   * @param creatorScores creatorScores or {@code null} for none
   */
  public Game setCreatorScores(java.util.List<java.lang.Long> creatorScores) {
    this.creatorScores = creatorScores;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEntityKey() {
    return entityKey;
  }

  /**
   * @param entityKey entityKey or {@code null} for none
   */
  public Game setEntityKey(java.lang.String entityKey) {
    this.entityKey = entityKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getInviteeKey() {
    return inviteeKey;
  }

  /**
   * @param inviteeKey inviteeKey or {@code null} for none
   */
  public Game setInviteeKey(java.lang.String inviteeKey) {
    this.inviteeKey = inviteeKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Long> getInviteeScores() {
    return inviteeScores;
  }

  /**
   * @param inviteeScores inviteeScores or {@code null} for none
   */
  public Game setInviteeScores(java.util.List<java.lang.Long> inviteeScores) {
    this.inviteeScores = inviteeScores;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getIsComplete() {
    return isComplete;
  }

  /**
   * @param isComplete isComplete or {@code null} for none
   */
  public Game setIsComplete(java.lang.Boolean isComplete) {
    this.isComplete = isComplete;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getIsSolo() {
    return isSolo;
  }

  /**
   * @param isSolo isSolo or {@code null} for none
   */
  public Game setIsSolo(java.lang.Boolean isSolo) {
    this.isSolo = isSolo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLastTouchDateTime() {
    return lastTouchDateTime;
  }

  /**
   * @param lastTouchDateTime lastTouchDateTime or {@code null} for none
   */
  public Game setLastTouchDateTime(java.lang.String lastTouchDateTime) {
    this.lastTouchDateTime = lastTouchDateTime;
    return this;
  }

  @Override
  public Game set(String fieldName, Object value) {
    return (Game) super.set(fieldName, value);
  }

  @Override
  public Game clone() {
    return (Game) super.clone();
  }

}
