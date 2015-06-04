//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/library/actor-cocoa-base/build/java/im/actor/model/api/updates/UpdateCallEnd.java
//


#include "IOSClass.h"
#include "IOSPrimitiveArray.h"
#include "J2ObjC_source.h"
#include "im/actor/model/api/updates/UpdateCallEnd.h"
#include "im/actor/model/droidkit/bser/Bser.h"
#include "im/actor/model/droidkit/bser/BserObject.h"
#include "im/actor/model/droidkit/bser/BserValues.h"
#include "im/actor/model/droidkit/bser/BserWriter.h"
#include "im/actor/model/network/parser/Update.h"
#include "java/io/IOException.h"

@interface APUpdateCallEnd () {
 @public
  NSString *callId_;
}

@end

J2OBJC_FIELD_SETTER(APUpdateCallEnd, callId_, NSString *)

@implementation APUpdateCallEnd

+ (APUpdateCallEnd *)fromBytesWithByteArray:(IOSByteArray *)data {
  return APUpdateCallEnd_fromBytesWithByteArray_(data);
}

- (instancetype)initWithNSString:(NSString *)callId {
  APUpdateCallEnd_initWithNSString_(self, callId);
  return self;
}

- (instancetype)init {
  APUpdateCallEnd_init(self);
  return self;
}

- (NSString *)getCallId {
  return self->callId_;
}

- (void)parseWithBSBserValues:(BSBserValues *)values {
  self->callId_ = [((BSBserValues *) nil_chk(values)) getStringWithInt:2];
}

- (void)serializeWithBSBserWriter:(BSBserWriter *)writer {
  if (self->callId_ == nil) {
    @throw new_JavaIoIOException_init();
  }
  [((BSBserWriter *) nil_chk(writer)) writeStringWithInt:2 withNSString:self->callId_];
}

- (NSString *)description {
  NSString *res = @"update CallEnd{";
  res = JreStrcat("$C", res, '}');
  return res;
}

- (jint)getHeaderKey {
  return APUpdateCallEnd_HEADER;
}

@end

APUpdateCallEnd *APUpdateCallEnd_fromBytesWithByteArray_(IOSByteArray *data) {
  APUpdateCallEnd_initialize();
  return ((APUpdateCallEnd *) BSBser_parseWithBSBserObject_withByteArray_(new_APUpdateCallEnd_init(), data));
}

void APUpdateCallEnd_initWithNSString_(APUpdateCallEnd *self, NSString *callId) {
  (void) APUpdate_init(self);
  self->callId_ = callId;
}

APUpdateCallEnd *new_APUpdateCallEnd_initWithNSString_(NSString *callId) {
  APUpdateCallEnd *self = [APUpdateCallEnd alloc];
  APUpdateCallEnd_initWithNSString_(self, callId);
  return self;
}

void APUpdateCallEnd_init(APUpdateCallEnd *self) {
  (void) APUpdate_init(self);
}

APUpdateCallEnd *new_APUpdateCallEnd_init() {
  APUpdateCallEnd *self = [APUpdateCallEnd alloc];
  APUpdateCallEnd_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(APUpdateCallEnd)
